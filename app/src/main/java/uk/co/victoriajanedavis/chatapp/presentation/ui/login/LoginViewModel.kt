package uk.co.victoriajanedavis.chatapp.presentation.ui.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import uk.co.victoriajanedavis.chatapp.domain.entities.TokenEntity
import uk.co.victoriajanedavis.chatapp.domain.interactors.LoginUser
import uk.co.victoriajanedavis.chatapp.domain.interactors.LoginUser.LoginParams
import uk.co.victoriajanedavis.chatapp.presentation.common.State
import uk.co.victoriajanedavis.chatapp.presentation.common.State.*
import javax.inject.Inject

class LoginViewModel @Inject constructor(
        private val loginUser: LoginUser
) : ViewModel() {

    private val loginUserLiveData = MutableLiveData<State<TokenEntity>>()
    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getLoginUserLiveData(): LiveData<State<TokenEntity>> = loginUserLiveData

    fun loginUser(username: String, password: String) {
        loginUserLiveData.postValue(ShowLoading)
        compositeDisposable.add(bindToUseCase(LoginParams(username, password)))
    }

    private fun bindToUseCase(loginParams: LoginParams) : Disposable {
        return loginUser.getSingle(loginParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ tokenEntity -> loginUserLiveData.postValue(ShowContent(tokenEntity)) },
                        { e -> loginUserLiveData.postValue(ShowError(e.toString())) })
    }
}