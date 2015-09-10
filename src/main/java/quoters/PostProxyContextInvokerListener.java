package quoters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;

/**
 * Created by antonnaumoff on 10.09.15.
 */
public class PostProxyContextInvokerListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ConfigurableListableBeanFactory configurableListableBeanFactory;

    @Override

    public void onApplicationEvent(ContextRefreshedEvent event) {

        ApplicationContext applicationContext = event.getApplicationContext();
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(name);
            String originalClassName = beanDefinition.getBeanClassName();
            try {
                Class<?> originalClass = Class.forName(originalClassName);
                Method[] methods = originalClass.getMethods();
                for (Method m :methods) {
                    if(m.isAnnotationPresent(PostProxy.class)){
                        Object bean = applicationContext.getBean(name);
                        Method currentMethod = bean.getClass().getMethod(m.getName(), m.getParameterTypes());
                        currentMethod.invoke(bean);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
