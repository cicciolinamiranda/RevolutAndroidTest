package com.revolut.androidexam.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.revolut.androidexam.R;
import com.revolut.androidexam.RevolutApplication;
import com.revolut.androidexam.base.LatestRateRule;
import com.revolut.androidexam.model.dto.RateDTO;
import com.revolut.androidexam.presenter.LatestRatePresenter;
import com.revolut.androidexam.util.Logger;
import com.revolut.androidexam.views.adapter.LatestRateAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.michaelrocks.paranoid.Obfuscate;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
@Obfuscate
public class MainActivity extends AppCompatActivity implements LatestRateRule.ILatestRateView {

    private static Logger LOGGER = Logger.build(MainActivity.class);

    @Inject
    LatestRatePresenter latestRatePresenter;
    @Inject
    LatestRateAdapter adapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LOGGER.log("MainActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RevolutApplication.getInjector().inject(this);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle(getString(R.string.label_rates));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        emptyView.setVisibility(INVISIBLE);
    }

    @Override
    protected void onResume() {
        LOGGER.log("MainActivity onStart");
        super.onResume();
        latestRatePresenter.attachView(this);

    }

    @Override
    protected void onPause() {
        LOGGER.log("MainActivity onStop");
        latestRatePresenter.detachView();
        super.onPause();
    }

    @Override
    public void onRateUpdated(List<RateDTO> list) {
        LOGGER.log("MainActivity onRateUpdated - " + list.toString());
        adapter.setVolatileRateValues(list);
        emptyView.setVisibility(INVISIBLE);
    }

    @Override
    public void onNoDataAvailable() {
        LOGGER.log("MainActivity onNoDataAvailable");
        emptyView.setVisibility(VISIBLE);
    }
}
