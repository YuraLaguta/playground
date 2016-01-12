package au.com.happydev.atlassiantest.messages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import au.com.happydev.atlassiantest.R;

/**
 * Created by laguta.yurii@gmail.com on 13/01/16.
 */
public class TransformationFragment extends Fragment {

    public static TransformationFragment newInstance() {

        Bundle args = new Bundle();

        TransformationFragment fragment = new TransformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transformation, container, false);
    }
}
