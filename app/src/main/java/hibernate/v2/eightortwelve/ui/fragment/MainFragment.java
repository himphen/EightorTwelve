package hibernate.v2.eightortwelve.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hibernate.v2.eightortwelve.C;
import hibernate.v2.eightortwelve.R;

/**
 * Created by himphen on 21/5/16.
 */
public class MainFragment extends BaseFragment {

	@BindView(R.id.card1AmountTv)
	TextView card1AmountTv;

	@BindView(R.id.card2AmountTv)
	TextView card2AmountTv;

	@BindView(R.id.card1CountET)
	EditText card1CountET;

	@BindView(R.id.card2CountET)
	EditText card2CountET;

	@BindView(R.id.card1TotalET)
	EditText card1TotalET;

	@BindView(R.id.card2TotalET)
	EditText card2TotalET;

	@BindView(R.id.card1Indicator)
	FrameLayout card1Indicator;

	@BindView(R.id.card2Indicator)
	FrameLayout card2Indicator;

	public MainFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		ButterKnife.bind(this, rootView);
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		card2TotalET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				boolean handled = false;
				if (actionId == EditorInfo.IME_ACTION_NEXT) {
					onClickCalc(v);
					handled = true;
				}
				return handled;
			}
		});
	}

	@OnClick(R.id.calcBtn)
	public void onClickCalc(View view) {

		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		view.clearFocus();

		card1Indicator.setVisibility(View.GONE);
		card2Indicator.setVisibility(View.GONE);

		Double card1Total;
		Double card2Total;
		Double card1Count;
		Double card2Count;

		try {
			card1Total = Double.parseDouble(card1TotalET.getText().toString());
			card2Total = Double.parseDouble(card2TotalET.getText().toString());
			card1Count = Double.parseDouble(card1CountET.getText().toString());
			card2Count = Double.parseDouble(card2CountET.getText().toString());

			Double card1Amount = C.round(card1Total / card1Count, 2);
			Double card2Amount = C.round(card2Total / card2Count, 2);

			if (card1Amount < 0) {
				card1AmountTv.setText("N/A");
			} else {
				card1AmountTv.setText(String.format("$%s", C.formatSignificant(card1Amount, card1Amount < 0.1 ? 1 : 2)));
			}

			if (card2Amount < 0) {
				card2AmountTv.setText("N/A");
			} else {
				card2AmountTv.setText(String.format("$%s", C.formatSignificant(card2Amount, card2Amount < 0.1 ? 1 : 2)));
			}

			if (card2Amount < card1Amount) {
				card2Indicator.setVisibility(View.VISIBLE);
			} else {
				card1Indicator.setVisibility(View.VISIBLE);
			}


		} catch (Exception e) {
			card1AmountTv.setText("N/A");
			card2AmountTv.setText("N/A");
		}
	}

	@OnClick(R.id.resetBtn)
	public void onClickReset(View view) {
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

		card1Indicator.setVisibility(View.GONE);
		card2Indicator.setVisibility(View.GONE);
		card1CountET.setText("");
		card2CountET.setText("");
		card1TotalET.setText("");
		card2TotalET.setText("");
		card1AmountTv.setText("$0.00");
		card2AmountTv.setText("$0.00");
	}

	@OnClick(R.id.helpBtn)
	public void onClickHelp() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_TEXT, "我剛用了「8罐定12罐抵D」APP 來格價，你也快來試試吧！！\n\nhttps://play.google.com/store/apps/details?id=hibernate.v2.eightortwelve");
		intent.setType("text/plain");
		startActivity(Intent.createChooser(intent, "分享"));
	}

	@OnClick(R.id.help2Btn)
	public void onClickHelp2() {
		Uri uri = Uri.parse("market://details?id=hibernate.v2.eightortwelve");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

	@OnClick(R.id.help3Btn)
	public void onClickHelp3() {
		Uri uri = Uri.parse("market://details?id=hibernate.v2.testyourandroid");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
}
