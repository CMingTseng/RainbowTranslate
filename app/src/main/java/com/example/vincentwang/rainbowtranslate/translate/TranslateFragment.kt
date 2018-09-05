package com.example.vincentwang.rainbowtranslate.translate


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.example.vincentwang.rainbowtranslate.R
import com.example.vincentwang.rainbowtranslate.TranslateModel
import com.example.vincentwang.rainbowtranslate.adapter.TranslateAdapter
import com.example.vincentwang.rainbowtranslate.data.WordTotalInfo
import com.example.vincentwang.rainbowtranslate.framework.BaseFragment
import kotlinx.android.synthetic.main.fragment_translate.*


/**
 * A simple [Fragment] subclass.
 * Use the [TranslateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TranslateFragment : BaseFragment(), FloatingSearchView.OnQueryChangeListener, FloatingSearchView.OnHomeActionClickListener, FloatingSearchView.OnSearchListener, TranslateContract.View {

    lateinit var translateAdapter: TranslateAdapter

    lateinit var mLayoutManager: RecyclerView.LayoutManager

    lateinit var wordTotalInfos: List<WordTotalInfo>


    var word: String? = null

    lateinit var presenter: TranslateContract.Presneter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = TranslatePresenter(this, TranslateModel())

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_translate, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_view.setOnHomeActionClickListener(this)
        search_view.setOnQueryChangeListener(this)
        search_view.setOnSearchListener(this)

        mLayoutManager = LinearLayoutManager(context)
        recyclerview_translate.layoutManager = mLayoutManager

        translateAdapter = TranslateAdapter()

        recyclerview_translate.adapter = translateAdapter

        if (word != null) {
            presenter.loadWordAllInfo(word)
        }
    }


    override fun onSearchTextChanged(oldQuery: String, newQuery: String) {
        word = newQuery
    }

    override fun onHomeClicked() {
        presenter.loadWordAllInfo(word)
    }

    override fun onSuggestionClicked(searchSuggestion: SearchSuggestion) {

    }

    override fun onSearchAction(currentQuery: String) {
        presenter.loadWordAllInfo(word)
    }

    override fun showWordTranslateInfo(wordTotalInfos: List<WordTotalInfo>) {
        this.wordTotalInfos = wordTotalInfos
        translateAdapter.setWordTotalInfo(wordTotalInfos)
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        fun newInstance(word: String): TranslateFragment {
            val fragment = TranslateFragment()
            fragment.word = word
            return fragment
        }
    }

}// Required empty public constructor
