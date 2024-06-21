package com.example.dependencyinjectionexempleapp.questionsList;

public interface ObservableViewMVC<ListenerType> extends ViewMVC {

    void registerListener(ListenerType listenerType);

    void unregisterListener(ListenerType listenerType);


}
