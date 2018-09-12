package com.mvp.vincentwang.rainbowtranslate.room.dao;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import com.mvp.vincentwang.rainbowtranslate.room.AppDatabase;
import com.mvp.vincentwang.rainbowtranslate.room.data.SearchTime;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordExample;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordInfo;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordMain;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordTotalInfo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;

import static org.hamcrest.Matchers.is;

public class WordInfoDaoTest {

    AppDatabase db;
    WordMain wordMain;
    WordInfo wordInfo;
    WordExample wordExample;
    SearchTime searchTime;
    @Before
    public void setUp() throws Exception {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class).build();
        wordMain = new WordMain("3","test",123);
         wordInfo = new WordInfo("2","3","4","5","6");
         wordExample = new WordExample("7","2","9","10");
        Calendar rightNow = Calendar.getInstance();
        searchTime = new SearchTime("11","3",rightNow.getTime());
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void insert() {
        db.wordMainDao().insert(wordMain);
        db.wordInfoDao().insert(wordInfo);
        db.wordExampleDao().insert(wordExample);
        Single<List<WordTotalInfo>> s = db.wordInfoDao().loadAllByIds("3");

        s.subscribe(new Consumer<List<WordTotalInfo>>() {
            @Override
            public void accept(List<WordTotalInfo> wordTotalInfos) throws Exception {
                Assert.assertThat(wordTotalInfos.get(0).getWordInfo().getWordinfoid(), is("2"));
                Assert.assertThat(wordTotalInfos.get(0).getWordInfo().getWordid(), is("3"));
                Assert.assertThat(wordTotalInfos.get(0).getWordInfo().getChinesemean(), is("4"));
                Assert.assertThat(wordTotalInfos.get(0).getWordInfo().getEnglishmean(), is("5"));
                Assert.assertThat(wordTotalInfos.get(0).getWordInfo().getType(), is("6"));
                Assert.assertThat(wordTotalInfos.get(0).getWordExamples().get(0).getExampleid(), is("7"));
                Assert.assertThat(wordTotalInfos.get(0).getWordExamples().get(0).getWordinfoid(), is("2"));
                Assert.assertThat(wordTotalInfos.get(0).getWordExamples().get(0).getExample(), is("9"));
                Assert.assertThat(wordTotalInfos.get(0).getWordExamples().get(0).getExampletranslate(), is("10"));
            }
        });
    }
    @Test
    public void InsertSearchTime(){
        db.wordMainDao().insert(wordMain);
        db.searchTimeDao().insert(searchTime);
//        Single<List<SearchTime>> s = db.searchTimeDao().loadAll();
        Calendar from = Calendar.getInstance();
        from.add(Calendar.MONTH, -1);
        Calendar to = Calendar.getInstance();
        to.add(Calendar.MONTH, 1);
        Single<List<SearchTime>> s = db.searchTimeDao().findWordMainBetweenDates(from.getTime(),to.getTime());

        s.subscribe(new Consumer<List<SearchTime>>() {
            @Override
            public void accept(List<SearchTime> searchTimes) throws Exception {
                Assert.assertThat(searchTimes.get(0).getWordid(), is("3"));
                Assert.assertThat(searchTimes.get(0).getSearchtimeid(), is("11"));
            }
        });
    }
}