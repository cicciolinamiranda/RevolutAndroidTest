package com.revolut.androidexam.views.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.revolut.androidexam.R;
import com.revolut.androidexam.RevolutApplication;
import com.revolut.androidexam.model.dto.RateDTO;
import com.revolut.androidexam.model.repository.LocalStorage;
import com.revolut.androidexam.util.Logger;
import com.revolut.androidexam.util.Mappers;
import com.revolut.androidexam.views.custom.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static com.revolut.androidexam.util.RateUtil.getCurrencyNameResByISO;
import static com.revolut.androidexam.util.RateUtil.getFlagResByISO;

public class LatestRateAdapter extends RecyclerView.Adapter<LatestRateAdapter.ViewHolder> {

    private static Logger LOGGER = Logger.build(LatestRateAdapter.class);

    private List<RateDTO> actualList = new ArrayList<>();
    private boolean binding;

    private RecyclerView mRecyclerView;

    @Inject
    public Context context;
    @Inject
    public LocalStorage storage;

    @Inject
    public LatestRateAdapter() {
        RevolutApplication.getInjector().inject(this);
        actualList.add(0, storage.getMainRate());
    }

    @NonNull
    @Override
    public LatestRateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false), new MyCustomEditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull LatestRateAdapter.ViewHolder viewHolder, int position) {
        binding = true;
        RateDTO current = actualList.get(position);
        viewHolder.myCustomEditTextListener.updatePosition(position);
        viewHolder.flagImageView.setImageResource(getFlagResByISO(current.name()));
        viewHolder.textIso.setText(current.name());
        viewHolder.textView.setText(getCurrencyNameResByISO(current.name()));
        viewHolder.cardView.setOnClickListener((view) -> smoothToFirstElementFromPosition(position));
        viewHolder.currencyEditText.setText(String.valueOf(current.value()));
        viewHolder.setFocusableIfNeed(position);

        binding = false;
    }

    private void smoothToFirstElementFromPosition(int position) {
        RateDTO newMainRate = actualList.get(position);

        actualList.remove(position);
        actualList.add(0, newMainRate);

        if (!binding) {
            mRecyclerView.scrollToPosition(0);
            notifyItemMoved(position, 0);
            new Handler().postDelayed(this::notifyDataSetChanged, 500);
        }

        storage.saveMainRate(newMainRate);
    }

    @Override
    public int getItemCount() {
        return actualList != null ? actualList.size() : 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
    }

    private void setDefaultRateValues() {
        LOGGER.log("LatestRateAdapter setDefaultRateValues");
        List<RateDTO> zeroList = new ArrayList<>();
        for (int i = 1; i < actualList.size(); i++) {
            zeroList.add(RateDTO.create(actualList.get(i).name(), 0.0));
        }
        setVolatileRateValues(zeroList);
    }

    public void setVolatileRateValues(List<RateDTO> rateDTOList) {
        LOGGER.log("LatestRateAdapter setDefaultRateValues");

        for (int index = actualList.size() - 1; index > 0; index--)
            actualList.remove(index);
        this.actualList.addAll(1, new ArrayList<>(rateDTOList));
        if (!binding) notifyItemRangeChanged(1, rateDTOList.size());

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.currency_edit_text)
        EditText currencyEditText;

        @BindView(R.id.flag_image_view)
        CircleImageView flagImageView;

        @BindView(R.id.text_iso)
        TextView textIso;

        @BindView(R.id.text_name)
        TextView textView;

        public MyCustomEditTextListener myCustomEditTextListener;

        public ViewHolder(View itemView, MyCustomEditTextListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            myCustomEditTextListener = listener;
            currencyEditText.addTextChangedListener(myCustomEditTextListener);
        }

        public void setFocusableIfNeed(int position){
            currencyEditText.setInputType(position == 0 ? TYPE_CLASS_NUMBER : 0);
        }

    }

    public class MyCustomEditTextListener implements TextWatcher {

        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i2, int i3) {

            if (position != 0) return;
            Double value = Mappers.parseDouble(s.toString());
            RateDTO updatedMainRate = RateDTO.create(storage.getMainRate().name(), value);
            storage.saveMainRate(updatedMainRate);
            actualList.remove(0);
            actualList.add(0, updatedMainRate);

            if (value.equals(0.0)) setDefaultRateValues();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
