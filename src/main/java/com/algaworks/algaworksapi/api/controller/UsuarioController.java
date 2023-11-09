package com.algaworks.algaworksapi.api.controller;

import com.algaworks.algaworksapi.api.converter.input.UsuarioModelInputConverter;
import com.algaworks.algaworksapi.api.converter.output.UsuarioModelOutputConverter;
import com.algaworks.algaworksapi.api.model.output.UsuarioModel;
import com.algaworks.algaworksapi.api.model.input.SenhaInput;
import com.algaworks.algaworksapi.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algaworksapi.api.model.input.UsuarioInput;
import com.algaworks.algaworksapi.api.openapi.controller.UsuarioControllerOpenApi;
import com.algaworks.algaworksapi.domain.model.Usuario;
import com.algaworks.algaworksapi.domain.repository.UsuarioRepository;
import com.algaworks.algaworksapi.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {

    @Autowired
    private CadastroUsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioModelInputConverter usuarioInputConverter;

    @Autowired
    private UsuarioModelOutputConverter usuarioOutputConverter;

    @GetMapping
    public List<UsuarioModel> listar() {
        return usuarioInputConverter.toCollectionModel(usuarioRepository.findAll());
    }

    @GetMapping("/{usuarioId}")
    public UsuarioModel buscar(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);

        return usuarioInputConverter.toModel(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = usuarioOutputConverter.toDomainObject(usuarioInput);
        usuario = usuarioService.salvar(usuario);

        return usuarioInputConverter.toModel(usuario);
    }

    @PutMapping("/{usuarioId}")
    public UsuarioModel atualizar(@PathVariable Long usuarioId,
                                  @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
        usuarioOutputConverter.copyToDomainObject(usuarioInput, usuarioAtual);
        usuarioAtual = usuarioService.salvar(usuarioAtual);

        return usuarioInputConverter.toModel(usuarioAtual);
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
        usuarioService.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
    }
}
