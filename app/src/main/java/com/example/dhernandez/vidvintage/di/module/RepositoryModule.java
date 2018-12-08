package com.example.dhernandez.vidvintage.di.module;

import com.example.dhernandez.vidvintage.repository.LocalStorageRepository.ILocalStorageRepository;
import com.example.dhernandez.vidvintage.repository.VintageRepository.IVintageRepository;
import com.example.dhernandez.vidvintage.repository.LocalStorageRepository.LocalStorageRepository;
import com.example.dhernandez.vidvintage.repository.VintageRepository.VintageRepository;

import dagger.Binds;
import dagger.Module;

/**
 * Created by dhernandez on 06/12/2018.
 */

@Module
public abstract class RepositoryModule {

    @Binds
    public abstract IVintageRepository provideVintageRepository(VintageRepository vintageRepository);

    @Binds
    public abstract ILocalStorageRepository provideLogalStorageRepository(LocalStorageRepository localStorageRepository);



}
