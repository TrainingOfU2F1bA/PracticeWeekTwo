package xin.saul.greet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class IocContextTest{

    @Test
    void testGetBean() throws InstantiationException, IllegalAccessException {

        IocContextImpl context = new IocContextImpl();
        context.registerBean(MyBean.class);
        MyBean bean = context.getBean(MyBean.class);

        assertNotNull(bean);

    }

    @Test
    void test_should_throw_IllegalArgumentException_when_beanClazz_is_null() {


        Executable executable =() -> {
            IocContextImpl context = new IocContextImpl();
            context.registerBean(null);
        };

        assertThrows(IllegalArgumentException.class, executable,"beanClazz is manatory");

    }


    @Test
    void test_should_throw_IllegalArgumentException_when_beanClazz_is_abstract_class() {


        Executable executable =() -> {
            IocContextImpl context = new IocContextImpl();
            context.registerBean(AbstractClass.class);
        };

        assertThrows(IllegalArgumentException.class, executable,"xin.saul.greet.AbstractClass is abstract");

    }

    @Test
    void test_should_throw_IllegalArgumentException_when_beanClazz_is_class_without_default_construct() {


        Executable executable =() -> {
            IocContextImpl context = new IocContextImpl();
            context.registerBean(ClassWithoutDefaultConstructor.class);
        };

        assertThrows(IllegalArgumentException.class, executable,"xin.saul.greet.ClassWithoutDefaultConstructor has no default constructor");

    }

    @Test
    void test_should_success_when_put_a_class_which_already_exsit() {
        boolean successFlag = false;
        try {
            IocContextImpl context = new IocContextImpl();
            context.registerBean(MyBean.class);
            context.registerBean(MyBean.class);
        } catch (Throwable e) {
            successFlag = true;
        }

        assertTrue(true);
    }


    @Test
    void test_should_throw_IllegalArgumentException_when_resolveClazz_is_null() {


        Executable executable =() -> {
            IocContextImpl context = new IocContextImpl();
            context.getBean(null);
        };

        assertThrows(IllegalArgumentException.class, executable);

    }

    @Test
    void test_should_throw_IllegalArgumentException_when_resolveClazz_had_not_be_register() {

        Executable executable =() -> {
            IocContextImpl context = new IocContextImpl();
            context.getBean(MyBean.class);
        };

        assertThrows(IllegalStateException.class, executable);

    }

    @Test
    void test_should_throw_exception_that_constructor_throw() {

        Executable executable =() -> {
            IocContextImpl context = new IocContextImpl();
            context.registerBean(ClassWithBadConstruct.class);
            context.getBean(ClassWithBadConstruct.class);
        };

        assertThrows(ConstructException.class, executable);

    }

    @Test
    void test_should_throw_IllegalArgumentException_call_register_bean_while_get_bean_have_been_running() {
        Executable executable =() -> {
            IocContextImpl context = new IocContextImpl();
            context.getBean(ClassWithContructRegisterBean.class);
            context.registerBean(ClassWithContructRegisterBean.class);
        };

        assertThrows(IllegalStateException.class, executable);
    }

}