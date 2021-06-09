package com.example.marketbot

import java.io.IOException
import java.net.Socket

class mServer {
    val mServerName = "192.168.0.10";
    val mServerPort  = 6789;
    var mSocket:Socket? = null

    /**
     * Метод для открытия соединения по адресу и порту
     *
     */
    fun openConnection(){
        closeConnection()
        try {
             mSocket = Socket(mServerName,mServerPort)
        }catch (e:IOException){

        }
    }

    /**
     * Метод для закрытия соединения
     *
     */
    private fun closeConnection(){
        if(mSocket == null && !mSocket?.isClosed!!){
            try {
                mSocket?.close()
            }catch (e:IOException){

            }finally {
                mSocket = null
            }
        }
        mSocket = null
    }

    /**
     * Метод для отправки координат
     *
     * @param xData: лист с X коориданатами
     * @param yData: лист с Y координатами
     */
    fun sendData(xData:ByteArray,yData:ByteArray ){
        if(mSocket == null || mSocket?.isClosed!!) {
            closeConnection()
        }
        try {
            mSocket?.getOutputStream()?.write(yData)
            mSocket?.getOutputStream()?.write(xData)
            mSocket?.getOutputStream()?.flush()
        }catch (e:IOException){

        }
    }

}

