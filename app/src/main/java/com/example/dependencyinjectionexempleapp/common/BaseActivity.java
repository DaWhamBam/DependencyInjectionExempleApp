package com.example.dependencyinjectionexempleapp.common;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dependencyinjectionexempleapp.MyApplication;
import com.example.dependencyinjectionexempleapp.dependencyInjection.CompositionRoot;

public class BaseActivity  extends AppCompatActivity {
    protected CompositionRoot getCompositionRoot() {
        return ((MyApplication) getApplication()).getCompositionRoot();
    }
}
