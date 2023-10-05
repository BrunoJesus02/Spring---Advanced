package com.algaworks.algaworksapi.infrastructure.service.storage;

import com.algaworks.algaworksapi.core.storage.StorageProperties;
import com.algaworks.algaworksapi.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;

public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            URL url = amazonS3.getUrl(
                    storageProperties.getS3().getBucket(),
                    getCaminhoArquivo(nomeArquivo));

            return FotoRecuperada.builder()
                    .url(url.toString())
                    .build();

        }  catch (Exception e) {
            throw new StorageException("Não foi possível recuperar arquivo da Amazon S3", e);
        }
    }

    @Override
    public void armazenar(NovaFoto foto) {
        try {
            String caminho = getCaminhoArquivo(foto.getNomeArquivo());

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(foto.getContentType());

            PutObjectRequest request = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    caminho,
                    foto.getInputStream(),
                    objectMetadata)
            .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(request);
        } catch (Exception e) {
            throw new StorageException("Não foi possível enviar arquivo para Amazon S3", e);
        }
    }

    private String getCaminhoArquivo(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            DeleteObjectRequest request = new DeleteObjectRequest(
                    storageProperties.getS3().getBucket(),
                    getCaminhoArquivo(nomeArquivo));

            amazonS3.deleteObject(request);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir arquivo na Amazon S3.", e);
        }
    }
}
