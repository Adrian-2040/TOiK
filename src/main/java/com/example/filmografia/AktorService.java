package com.example.filmografia;

import com.example.filmografia.repository.AktorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AktorService {

    @Autowired
    private AktorRepository aktorRepository;

    public Aktor dodajAktora(Aktor aktor) {
        return aktorRepository.save(aktor);
    }

    public Aktor aktualizujAktora(Aktor aktor) {
        return aktorRepository.save(aktor);
    }

    public void usunAktora(Long id) {
        aktorRepository.deleteById(id);
    }

    public Aktor znajdzAktoraPoId(Long id) {
        return aktorRepository.findById(id).orElse(null);
    }

    public List<Aktor> znajdzWszystkichAktorow() {
        return aktorRepository.findAll();
    }
}
