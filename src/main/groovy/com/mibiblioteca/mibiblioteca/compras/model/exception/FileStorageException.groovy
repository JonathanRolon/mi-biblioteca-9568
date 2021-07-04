package com.mibiblioteca.mibiblioteca.compras.model.exception

class FileStorageException extends RuntimeException{

    FileStorageException(String errorMessage){
        super(errorMessage);
    }
}
