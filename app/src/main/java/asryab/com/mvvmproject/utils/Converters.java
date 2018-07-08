package asryab.com.mvvmproject.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.binding.BindableString;
import asryab.com.mvvmproject.binding.BindingActionClick;
import asryab.com.mvvmproject.screens.authorization.signup.AuthoCompleteWatcher;
import asryab.com.mvvmproject.screens.authorization.signup.AutoCompleteText;
import asryab.com.mvvmproject.screens.authorization.signup.EdittextBackgroundListener;
import asryab.com.mvvmproject.viewmodels.authorization.signup.SignUpVM;
import rx.functions.Action1;

public abstract class Converters {

    @BindingAdapter({"pagerAdapter"})
    public static void setPagerAdapter(final ViewPager viewPager, final PagerAdapter viewPagerAdapter) {
        viewPager.setAdapter(viewPagerAdapter);
    }

    @BindingAdapter({"recyclerScrollListener"})
    public static void bindRecyclerScrollistener(final RecyclerView recyclerView, RecyclerView.OnScrollListener scrollListener) {
        recyclerView.setOnScrollListener(scrollListener);
    }

    @BindingAdapter({"onTabSelect"})
    public static void setTabSelected(final TabLayout tabLayout, final Action1<Integer> action) {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                action.call(tabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @BindingAdapter({"onPageSelected"})
    public static void setPageSelected(final ViewPager viewPager, final Action1<Integer> action) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                action.call(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @BindingAdapter({"selectTab"})
    public static void selectHomeTab(final RadioGroup radioGroup, final int position) {
        if (position >= 0 && !((RadioButton) radioGroup.getChildAt(position)).isChecked())
            ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
    }

    @BindingAdapter({"onTabSelected"})
    public static void setTabSelected(final RadioGroup radioGroup, final Action1<Integer> actionTabPosition) {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> actionTabPosition.call(group.indexOfChild(group.findViewById(checkedId))));
    }

    @BindingAdapter(value = {"glideImage", "glideDefault", "glidePlaceholder", "glideNoCache", "glideTransform"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String imageUri, Drawable defaultDrawable, Drawable placeHolderDrawable, boolean noCache, BitmapTransformation transformation) {
        final Context context = imageView.getContext();

        if (!TextUtils.isEmpty(imageUri)) {
            DrawableRequestBuilder glideBuilder = Glide.with(context)
                    .load(imageUri)
                    .placeholder(placeHolderDrawable)
                    .error(defaultDrawable)
                    .skipMemoryCache(noCache);

            if (transformation != null)
                glideBuilder.transform(transformation);

            glideBuilder.into(imageView);
        }
        else imageView.setImageDrawable(defaultDrawable);
    }

    @BindingAdapter({"recyclerManager"})
    public static void setRecyclerManager(final RecyclerView view,
                                          @NonNull RecyclerView.LayoutManager manager) {
        view.setLayoutManager(manager);
    }

    @BindingAdapter({"recyclerAdapter"})
    public static void setRecyclerAdapter(final RecyclerView view,
                                          @NonNull RecyclerView.Adapter adapter) {
        view.setAdapter(adapter);
    }

    @BindingAdapter({"recyclerDecoration"})
    public static void setRecyclerAdapter(final RecyclerView view,
                                          @NonNull RecyclerView.ItemDecoration decoration) {
        view.addItemDecoration(decoration);
    }

    @BindingAdapter({"spinnerAdapter"})
    public static void setAdapter(final Spinner spinner,
                                  @NonNull ArrayAdapter adapter) {
        spinner.setAdapter(adapter);
    }

    @BindingAdapter({"autoCompleteAdapter"})
    public static void setAdapter(final AutoCompleteTextView autoCompleteTextView,
                                  @NonNull ArrayAdapter adapter) {
        autoCompleteTextView.setAdapter(adapter);
    }

    @BindingAdapter({"textWatcher"})
    public static void setTextWatcher(final AutoCompleteText view,
                                      @NonNull AuthoCompleteWatcher watcher) {
        view.addTextChangedListener(watcher);
        watcher.setShowDrop(view);
    }

    @BindingAdapter({"textWatcher"})
    public static void setTextWatcher(final AutoCompleteTextView view,
                                      @NonNull TextWatcher watcher) {
        view.addTextChangedListener(watcher);
    }

    @BindingAdapter({"spinnerListener"})
    public static void bindSpinner(Spinner spinner, final AdapterView.OnItemSelectedListener listener) {
        spinner.setOnItemSelectedListener(listener);
    }

    @BindingAdapter({"autocompleteListener"})
    public static void bindAutocomplete(AutoCompleteTextView view, final AdapterView.OnItemClickListener listener) {
        view.setOnItemClickListener(listener);
    }

    @BindingAdapter({"bind:binding"})
    public static void bindEditTextField(TextView view, final BindableString bindableString) {
        if (view.getTag(R.id.binded) == null) {
            view.setTag(R.id.binded, true);
            view.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(view.getTag(R.string.background_key) != null && (boolean) view.getTag(R.string.background_key)) {
                        view.setTag(R.string.background_key, false);
                        view.setBackground(view.getContext().getResources().getDrawable(R.drawable.edit_normal_background));
                    }
                    bindableString.set(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        String newValue = bindableString.get();
        if (!view.getText().toString().equals(newValue)) {
            view.setText(newValue);
        }
    }

    @BindingAdapter({"bind:backgroundListener", "bind:type"})
    public static void setEditField(final View view, final EdittextBackgroundListener listener,
                                    final SignUpVM.FieldType type) {
        listener.setEditField(type, view);
    }

    @BindingAdapter({"bindClick", "bindModel"})
    public static void setOnActionListener(final View view,
                                           @NonNull BindingActionClick bindingActionClick, Object model) {
        view.setOnClickListener(v -> bindingActionClick.onBindClick(v, model));
    }

    @BindingAdapter({"countSymbolsListener"})
    public static void setCountSymbolsListener(final EditText editText, final Action1<String> symbols) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (symbols != null)
                    symbols.call(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @BindingAdapter({"checked"})
    public static void checked(final CheckBox checkBox,
                               final Action1<Boolean> checkedAction) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkedAction.call(isChecked);
            }
        });
    }

}
