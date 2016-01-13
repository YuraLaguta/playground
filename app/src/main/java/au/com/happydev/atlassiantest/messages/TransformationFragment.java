package au.com.happydev.atlassiantest.messages;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import au.com.happydev.atlassiantest.BusProvider;
import au.com.happydev.atlassiantest.R;
import au.com.happydev.atlassiantest.core.ErrorEvent;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by laguta.yurii@gmail.com on 13/01/16.
 */
public class TransformationFragment extends Fragment {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATE_READY, STATE_PROCESSING})
    public @interface State {}
    private static final int STATE_READY = 1;
    private static final int STATE_PROCESSING = 2;

    @State
    private int mState = STATE_READY;

    @Bind(R.id.transformation_edit_text)
    EditText mInputEditText;
    @Bind(R.id.transformation_msg_text_view)
    TextView mMsgTextView;
    @Bind(R.id.transformation_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.transformation_send_btn)
    Button mButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transformation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mMsgTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getBus().register(this);
    }

    @Override
    public void onPause() {
        BusProvider.getBus().unregister(this);
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @OnClick(R.id.transformation_send_btn)
    void onTransformClicked(Button btn) {
        setState(STATE_PROCESSING);
        TransformationIntentService.sendMessage(getActivity(), mInputEditText.getText().toString());
    }

    @OnTextChanged(R.id.transformation_edit_text)
    void onAfterTextChanged(Editable editable) {
        updateTransformBtnState(editable.length());
    }

    private void updateTransformBtnState(int inputSize) {
        mButton.setEnabled(mState == STATE_READY && inputSize > 0);
    }

    @Subscribe
    public void onMessageProcessed(Message message) {
        mMsgTextView.setText(MessageTransformerProvider.getMessageTransformer().toJson(message));
        mInputEditText.setText("");
        setState(STATE_READY);
    }

    @Subscribe
    public void onMessageProcessError(ErrorEvent event) {
        setState(STATE_READY);
    }

    private void setState(@State int state) {
        mState = state;
        if (state == STATE_READY) {
            mInputEditText.setEnabled(true);
            mProgressBar.setVisibility(View.INVISIBLE);
        } else if (state == STATE_PROCESSING) {
            mInputEditText.setEnabled(false);
            mProgressBar.setVisibility(View.VISIBLE);
        }
        updateTransformBtnState(mInputEditText.getText().length());
    }

}
