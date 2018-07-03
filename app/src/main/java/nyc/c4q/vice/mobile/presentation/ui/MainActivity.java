package nyc.c4q.vice.mobile.presentation.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import nyc.c4q.vice.mobile.R;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.container)
    ViewGroup container;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    private View homeView;
    private View favoritesView;
    private View searchView;
    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        actionBar = getSupportActionBar();

        LayoutInflater layoutInflater = getLayoutInflater();

        homeView = layoutInflater.inflate(R.layout.view_home, null);
        favoritesView = layoutInflater.inflate(R.layout.view_favorites, null);
        searchView = layoutInflater.inflate(R.layout.view_search, null);

        // set the initial view and action bar text
        container.addView(homeView);
        actionBar.setTitle(R.string.home_title);

        // logic to switch views upon tab click
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            // based on tab click, decide whether to show home or favorites view
            switch (menuItem.getItemId()) {
                case R.id.action_home:
                    container.removeAllViews();
                    container.addView(homeView);
                    actionBar.setTitle(R.string.home_title);
                    break;
                case R.id.action_favorites:
                    container.removeAllViews();
                    container.addView(favoritesView);
                    actionBar.setTitle(R.string.favorites_title);
                    break;
                case R.id.action_search:
                    container.removeAllViews();
                    container.addView(searchView);
                    actionBar.setTitle(R.string.search_title);
                    break;
                default:
                    throw new IllegalStateException(
                            "Unknown menu item id: " + getResources().getResourceName(menuItem.getItemId())
                    );
            }

            return true;
        });
    }
}