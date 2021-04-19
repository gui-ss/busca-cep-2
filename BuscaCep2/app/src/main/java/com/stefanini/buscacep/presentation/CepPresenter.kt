package com.stefanini.buscacep.presentation

import com.stefanini.buscacep.MainActivity
import com.stefanini.buscacep.datasource.CepRemoteDataSource
import com.stefanini.buscacep.model.Cep

class CepPresenter(mainActivity: MainActivity, data: CepRemoteDataSource): CepRemoteDataSource.CepCallback{

    var view: MainActivity = mainActivity
    var dataSource: CepRemoteDataSource = data

    fun findCepBy(cep: String) {
        this.view.showProgressBar()
        this.dataSource.findCepBy(this, cep)
    }

    override fun onSucess(response: Cep?) {
        if (response != null){
            view.showCep(response)
        }
    }

    override fun onError(message: String?) {
        if (message != null){
            view.showFailure(message)
        }
    }

    override fun onComplete() {
        view.hideProgressBar()
    }

}