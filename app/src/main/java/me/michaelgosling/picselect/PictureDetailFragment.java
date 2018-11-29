package me.michaelgosling.picselect;

import android.app.Activity;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.michaelgosling.picselect.content.Content;

/**
 * A fragment representing a single Picture detail screen.
 * This fragment is either contained in a {@link PictureListActivity}
 * in two-pane mode (on tablets) or a {@link PictureDetailActivity}
 * on handsets.
 */
public class PictureDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy title this fragment is presenting.
     */
    private Content.Item mItem;



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PictureDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy title specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load title from a title provider.
            mItem = Content.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));


            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(getString(mItem.title));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.picture_detail, container, false);

        if (mItem != null) {
            ((ImageView) rootView.findViewById(R.id.picture_detail)).setImageDrawable(
                    getResources().getDrawable(mItem.resourceID));
        }

        return rootView;
    }
}
