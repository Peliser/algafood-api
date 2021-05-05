package com.algaworks.algafood.infrastructure.service.storage;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private AmazonS3 s3;

    @Autowired
    private StorageProperties properties;

    @Override
    public void salvar(Foto foto) {
        try {
            String caminhoArquivo = getCaminhoArquivo(foto.getNomeArquivo());
            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(foto.getContentType());
            var putObjectRequest = new PutObjectRequest(properties.getS3().getBucket(), caminhoArquivo,
                    foto.getArquivo(), objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead);

            s3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível enviar arquivo para Amazon S3.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
            var deleteObjectRequest = new DeleteObjectRequest(properties.getS3().getBucket(), caminhoArquivo);

            s3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir arquivo na Amazon S3.", e);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
        var url = s3.getUrl(properties.getS3().getBucket(), caminhoArquivo);

        return FotoRecuperada.builder().url(url.toString()).build();
    }

    private String getCaminhoArquivo(String nomeArquivo) {
        return String.format("%s/%s", properties.getS3().getDiretorioFotos(), nomeArquivo);
    }

}
