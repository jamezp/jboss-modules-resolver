/*
 * JBoss, Home of Professional Open Source.
 *
 * Copyright 2019 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.modules.resolver;

import java.util.Objects;

/**
 * @author <a href="mailto:jperkins@redhat.com">James R. Perkins</a>
 */
@SuppressWarnings("unused")
public class ClassLoaderResolverFactory {
    private static volatile ClassLoaderResolver INSTANCE;

    /**
     * Returns the current instance of the {@link ClassLoaderResolver}.
     *
     * @return the resolver used to resolve class loaders for modules
     */
    @SuppressWarnings("Convert2Lambda")
    public static ClassLoaderResolver getInstance() {
        ClassLoaderResolver result = INSTANCE;
        if (INSTANCE == null) {
            synchronized (ClassLoaderResolverFactory.class) {
                if (INSTANCE == null) {
                    result = INSTANCE = new ClassLoaderResolver() {
                        @Override
                        public ClassLoader resolve(final String name) throws IllegalArgumentException {
                            return JdkSpecific.resolve(Objects.requireNonNull(name, "The module name cannot be null."));
                        }
                    };
                }
            }
        }
        return result;
    }

    /**
     * Sets the class loader resolved to use for resolving class loaders of modules. If set to {@code null} a default
     * resolver will be used.
     *
     * @param resolver the class loader resolver to use or {@code null} to use a default class loader resolver
     */
    @SuppressWarnings("unused")
    public static void setClassLoaderResolver(final ClassLoaderResolver resolver) {
        INSTANCE = resolver;
    }
}
