package com.foxy.draganddraw;

import androidx.fragment.app.Fragment;

public class DragAndDrawActivity extends SingleFragmentActivity {

    // запуск фрагмента
    @Override
    protected Fragment createFragment() {
        return DragAndDrawFragment.newInstance();
    }
}
