package com.habibokanla.navi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * 2016
 * Created by habibokanla on 14/05/16.
 */
public class Navi {

    Subject<Object, Object> bus = PublishSubject.create();
    HashMap<Object, Subscription> subscriptionHashMap = new HashMap<>();

    static class EventListener implements Action1<Object> {
        private final List<Method> methods;
        private final Object baseClass;

        public EventListener(Object baseClass, List<Method> methods) {
            this.baseClass = baseClass;
            this.methods = methods;
        }

        @Override
        public void call(Object o) {
            for (Method method : methods) {
                Class<?> type = method.getParameterTypes()[0];
                if (type.isInstance(o)) {
                    try {
                        method.invoke(baseClass, type.cast(o));
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                }
            }
        }
    }

    //Todo: Thread subscriptions.

    public void register(Object object) {
        if (subscriptionHashMap.containsKey(object)) {
            throw new IllegalStateException("Already registered");
        }
        Method[] methods = object.getClass().getDeclaredMethods();
        List<Method> executableMethods = new ArrayList<>();
        for (Method method : methods) {
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                if (annotation.annotationType() == Listen.class) {
                    if (method.getParameterTypes().length != 1) {
                        throw new IllegalStateException("Listen annotated method should only have 1 param");
                    } else {
                        executableMethods.add(method);
                    }
                }
            }
        }

        subscriptionHashMap.put(object, bus.subscribe(new EventListener(object, executableMethods)));
    }

    public void post(Object event) {
        bus.onNext(event);
    }

    public void unRegister(Object object) {
        Subscription subscription = subscriptionHashMap.remove(object);
        if (subscription != null) {
            subscription.unsubscribe();
        } else {
            throw new IllegalStateException("Couldn't find subscription, did you call register?");
        }
    }
}
