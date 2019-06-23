package com.yongji.walmartlabs.ui;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import com.yongji.walmartlabs.R;
import com.yongji.walmartlabs.config.LifecycleComponent;
import com.yongji.walmartlabs.databinding.ActivityProductsBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityProductsBinding activityProductsBinding;

    /**
     * This fragment represents the current fragment being displayed on the view.
     */
    protected Fragment mContent;

    /**
     * This is a counter variable to count the number of fragments in the stack of this Activity.
     */
    protected int FragmentStack;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentStack = 0; // to count how many fragments are in the stack

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        activityProductsBinding = DataBindingUtil.setContentView(this, R.layout.activity_products);
        activityProductsBinding.getRoot();
        renderView();
        getLifecycle().addObserver(new LifecycleComponent());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                popFragmentFromStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void renderView() {
        setupToolbar();
        addFragmentToStack(new ProductsFragment());
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Method that removes a fragment from the stack.
     */
    public void popFragmentFromStack() {

        //https://stackoverflow.com/questions/7575921/illegalstateexception-can-not-perform-this-action-after-onsaveinstancestate-wit
        try {
            getSupportFragmentManager()
                    .popBackStackImmediate();

            // update mContent with the fragment currently at the frame
            mContent = getSupportFragmentManager().findFragmentById(R.id.frame);
            FragmentStack--;
        } catch (IllegalStateException ignored) {
            // There's no way to avoid getting this if saveInstanceState has already been called.
        }
    }

    public void switchFragment(Fragment fragment) {
        //When Fragment B replaces Fragment A
        //FYI: https://developer.android.com/training/basics/fragments/fragment-ui
        mContent = fragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.frame, fragment);
        // Commit the transaction
        //https://stackoverflow.com/questions/42688194/commitallowingstateloss-and-commit-fragment
        transaction.commitAllowingStateLoss();
    }

    /**
     * Method that adds a fragment to the fragment stack for easy navigation
     */
    public void addFragmentToStack(Fragment fragment) {
        mContent = fragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        FragmentStack++;
    }

    /**
     * This helper method changes the left corner icon to a back button. When clicked, it
     * will go back on the navigation process.
     */
    public void setBackButton(){

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_24dp);
    }

    /**
     * Helper function in charge of enabling the up button for certain fragments.
     */
    public void enableHomeAsUp(){
        // Get a support ActionBar corresponding to this toolbar
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Helper function that disables the up button for certain fragments.
     */
    public void disableHomeAsUp(){
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        try {
            ab.setDisplayHomeAsUpEnabled(false);
        } catch (NullPointerException npe){
            Log.e("disableHomeAsUp", npe.toString());
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

}
