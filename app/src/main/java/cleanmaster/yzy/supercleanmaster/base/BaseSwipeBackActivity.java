package cleanmaster.yzy.supercleanmaster.base;

import android.os.Bundle;
import android.view.View;

import cleanmaster.yzy.supercleanmaster.swipeback.SwipeBackActivityBase;
import cleanmaster.yzy.supercleanmaster.swipeback.SwipeBackActivityHelper;
import cleanmaster.yzy.supercleanmaster.swipeback.SwipeBackLayout;
import cleanmaster.yzy.supercleanmaster.swipeback.Utils;

public abstract class BaseSwipeBackActivity extends BaseActivity implements SwipeBackActivityBase {

    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
