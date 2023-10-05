package com.algaworks.algaworksapi.infrastructure.service.storage;

import com.algaworks.algaworksapi.core.storage.StorageProperties;
import com.algaworks.algaworksapi.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import java.nio.file.Files;
import java.nio.file.Path;

public class LocalFotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armazenar(NovaFoto foto) {
        Path arquivoPath = getArquivoPath(foto.getNomeArquivo());

        try {
            FileCopyUtils.copy(foto.getInputStream(), Files.newOutputStream(arquivoPath));
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenar arquivo.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            Path arquivoPath = getArquivoPath(nomeArquivo);
            Files.deleteIfExists(arquivoPath);
        } catch (Exception e) {
            throw new StorageException("Não foi possível deletar arquivo.", e);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            Path path = getArquivoPath(nomeArquivo);

            return FotoRecuperada.builder()
                    .inputStream(Files.newInputStream(path))
                    .build();
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar arquivo.", e);
        }
    }


    private Path getArquivoPath(String nomeArquivo) {
        return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
    }
}
