package me.michaelgosling.picselect;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import me.michaelgosling.picselect.content.Content;

import java.util.List;

/**
 * An activity representing a list of Pictures. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PictureDetailActivity} representing
 * item resourceID. On tablets, the activity presents the list of items and
 * item resourceID side-by-side using two vertical panes.
 */
public class PictureListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private static SharedPreferences prefs;
    private static SharedPreferences.Editor prefsEditor;
    private static int selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        prefs = getSharedPreferences("PicSelectPrefs", 0);
        prefsEditor = prefs.edit();

        selectedColor = getColor(R.color.selectedItem);

        if (findViewById(R.id.picture_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.picture_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, Content.ITEMS, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final PictureListActivity mParentActivity;
        private final List<Content.Item> mValues;
        private final boolean mTwoPane;
        private String mSelectedItem;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefsEditor.putString("ITEM_ID", ((Content.Item) view.getTag()).getItemId()).commit();
                Content.Item item = (Content.Item) view.getTag();
                Animation anim = new AlphaAnimation(0.0f, 1.0f);
                anim.setDuration(50);
                anim.setStartOffset(20);
                anim.setRepeatMode(Animation.REVERSE);
                anim.setRepeatCount(2);
                view.startAnimation(anim);
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(PictureDetailFragment.ARG_ITEM_ID, item.id);
                    PictureDetailFragment fragment = new PictureDetailFragment();
                    fragment.setArguments(arguments);

                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter, R.anim.exit)
                            .replace(R.id.picture_detail_container, fragment)
                            .commit();

                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, PictureDetailActivity.class);
                    intent.putExtra(PictureDetailFragment.ARG_ITEM_ID, item.id);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(PictureListActivity parent,
                                      List<Content.Item> items,
                                      boolean twoPane) {

            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (prefs.getString("ITEM_ID", null) != null)
                mSelectedItem = prefs.getString("ITEM_ID", null);

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.picture_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            if (mValues.get(position).id.equals(mSelectedItem))
                holder.itemView.setBackgroundColor(selectedColor);

            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mParentActivity.getString(mValues.get(position).title));

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);


        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.title);
            }
        }
    }
}
