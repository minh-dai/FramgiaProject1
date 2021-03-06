package com.framgia.music_29.screen.home.online;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.framgia.music_29.R;
import com.framgia.music_29.data.model.Genre;
import com.framgia.music_29.data.model.Song;
import com.framgia.music_29.screen.genre.GenreActivity;
import com.framgia.music_29.screen.home.HomeActivity;
import com.framgia.music_29.screen.player.PlayerActivity;
import com.framgia.music_29.screen.service.MusicService;
import com.framgia.music_29.utils.ConstantApi;
import com.framgia.music_29.utils.OnClickItemListener;
import java.util.ArrayList;
import java.util.List;

public class OnlineFragment extends Fragment
        implements OnlineFragmentContract.View, View.OnClickListener, OnClickItemListener,
        HomeActivity.IPassSongOnline {

    public static final String EXTRA_GENRE_SONG = "com.framgia.music_29.EXTRA_GENRE_SONG";
    public static final String EXTRA_GENRE_STATUS = "com.framgia.music_29.EXTRA_STAUTS";
    public static final String EXTRA_GENRE_LOCAL = "com.framgia.music_29.EXTRA_GENRE_LOCAL";
    private RecyclerViewAdapter mAdapterMusics;
    private RecyclerViewAdapter mAdapterAudios;
    private OnlineFragmentContract.Presenter mPresenter;
    private Song mSong;
    private TextView mTextMusics;
    private TextView mTextAudios;
    private TextView mTextAlternativerock;
    private TextView mTextAmbients;
    private TextView mTextClassicals;
    private TextView mTextCountrys;
    private HomeActivity mActivity;
    private boolean mLocal;
    private boolean mIsPlay;

    public static OnlineFragment newInstance() {
        return new OnlineFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_online, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initComponent();
        setDataListGenres();
        setAdapterAudios(view);
        setApdaterMusics(view);
        registerEventListener();
    }

    private void registerEventListener() {
        mTextAlternativerock.setOnClickListener(this);
        mTextMusics.setOnClickListener(this);
        mTextAudios.setOnClickListener(this);
        mTextAmbients.setOnClickListener(this);
        mTextClassicals.setOnClickListener(this);
        mTextCountrys.setOnClickListener(this);
    }

    private void setAdapterAudios(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_audios);
        LinearLayoutManager layoutmanager =
                new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutmanager);
        recyclerView.setAdapter(mAdapterAudios);
    }

    private void setApdaterMusics(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_musics);
        LinearLayoutManager layoutmanager =
                new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutmanager);
        recyclerView.setAdapter(mAdapterMusics);
    }

    private void initView(View view) {
        mTextMusics = view.findViewById(R.id.text_more_musics);
        mTextAudios = view.findViewById(R.id.text_more_audios);
        mTextAmbients = view.findViewById(R.id.text_ambients);
        mTextClassicals = view.findViewById(R.id.text_classicals);
        mTextCountrys = view.findViewById(R.id.text_countries);
        mTextAlternativerock = view.findViewById(R.id.text_alternativerock);
    }

    private void initComponent() {
        mPresenter = new OnlineFragmentPresenter();
        mPresenter.setView(this);

        mAdapterMusics = new RecyclerViewAdapter(getContext());
        mAdapterMusics.setClickItemListener(this);

        mAdapterAudios = new RecyclerViewAdapter(getContext());
        mAdapterAudios.setClickItemListener(this);

        mActivity = (HomeActivity) getActivity();
        mActivity.setPassSongViewPaper(this);
    }

    private void setDataListGenres() {
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            List<Song> musics = intent.getParcelableArrayListExtra(ConstantApi.GENRE_ALL_MUSIC);
            List<Song> audios = intent.getParcelableArrayListExtra(ConstantApi.GENRE_ALL_AUDIO);
            if (musics != null && audios != null) {
                mAdapterMusics.setSongs(musics);
                mAdapterAudios.setSongs(audios);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mLocal = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_more_musics:
                onStartGenreAvtivity(ConstantApi.GENRE_ALL_MUSIC);
                break;
            case R.id.text_more_audios:
                onStartGenreAvtivity(ConstantApi.GENRE_ALL_AUDIO);
                break;
            case R.id.text_ambients:
                onStartGenreAvtivity(ConstantApi.GENRE_AMBIENT);
                break;
            case R.id.text_classicals:
                onStartGenreAvtivity(ConstantApi.GENRE_CLASSICAL);
                break;
            case R.id.text_countries:
                onStartGenreAvtivity(ConstantApi.GENRE_COUNTRY);
                break;
            case R.id.text_alternativerock:
                onStartGenreAvtivity(ConstantApi.GENRE_ALTERNATIVEROCK);
                break;
        }
    }

    private void onStartGenreAvtivity(String genre) {
        Genre genre1 = new Genre();
        genre1.setName(genre);
        genre1.setSongs(new ArrayList<Song>());
        startActivity(GenreActivity.getGenreIntent(getContext(), genre1, mSong, mLocal, mIsPlay));
    }

    @Override
    public void onClick(List<Song> songs, int position) {
        startActivity(PlayerActivity.getPlayerIntent(getContext(), songs.get(position), mLocal));
        getContext().startService(MusicService.getInstance(getContext(), songs, position, false));
    }

    @Override
    public void passSong(Song song) {
        mSong = song;
    }

    @Override
    public void passStatus(boolean isPLay) {
        mIsPlay = isPLay;
    }
}
