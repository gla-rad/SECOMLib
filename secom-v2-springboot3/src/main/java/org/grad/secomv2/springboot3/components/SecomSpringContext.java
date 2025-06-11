/*
 * Copyright (c) 2025 GLA Research and Development Directorate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.grad.secomv2.springboot3.components;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * The SECOM Spring Module Context Component.
 * <p>
 * This allows the SECOM Spring module classes to access Spring beans regardless
 * of whether they themselves are initialised as components or not.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
@Component
public class SecomSpringContext implements ApplicationContextAware {

    // Class Variables
    private static ApplicationContext context;

    /**
     * Returns the Spring managed bean instance of the given class type if it exists.
     * Returns null otherwise.
     *
     * @param beanClass     The class of the bean to be retrieved
     * @return the spring bean retrieved
     */
    public static <T extends Object> T getBean(Class<T> beanClass) {
        // Sanity Check
        if(context == null) {
            return null;
        }
        try {
            return context.getBean(beanClass);
        } catch (NoSuchBeanDefinitionException ex) {
            return null;
        }
    }

    /**
     * Overrides the default application context aware function to
     * @param context the ApplicationContext object to be used by this object
     *
     * @throws BeansException for invalid application context
     */
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        // store ApplicationContext reference to access required beans later on
        this.setContext(context);
    }

    /**
     * Private method context setting (better practice for setting a static field
     * in a bean instance - see comments of this article for more info).
     */
    private static synchronized void setContext(ApplicationContext context) {
        SecomSpringContext.context = context;
    }

}
