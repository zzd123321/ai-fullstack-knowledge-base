package homework1;

@FunctionalInterface
interface GreetingFunctional extends Greeting {
    @Override
    void sayHello();
}