package dev.codefortress.core.easy_config_ui;

import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controlador REST que expone las propiedades configurables
 * detectadas en clases marcadas con @AutoLoadable y @EasyConfigProperty.
 *
 * Este endpoint está disponible solo en módulos Pro con UI embebida.
 */
@RestController
@RequestMapping("/easy-config")
public class EasyConfigController {

    private final EasyConfigStore configStore;

    public EasyConfigController(EasyConfigStore configStore) {
        this.configStore = configStore;
    }

    /**
     * Lista todas las propiedades registradas junto con su valor actual.
     */
    @GetMapping
    public List<Map<String, Object>> list() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (EasyConfigScanner.ConfigMetadata meta : EasyConfigScanner.getAll()) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("key", meta.key());
            entry.put("description", meta.description());
            entry.put("value", configStore.get(meta.key()));
            result.add(entry);
        }
        return result;
    }

    /**
     * Actualiza el valor de una propiedad existente.
     *
     * @param key nombre de la propiedad
     * @param body JSON con {"value": nuevoValor}
     * @return respuesta con el valor actualizado
     */
    @PostMapping("/{key}")
    public Map<String, Object> update(@PathVariable String key, @RequestBody Map<String, String> body) {
        if (!configStore.contains(key)) {
            throw new RuntimeException("La clave '" + key + "' no está registrada.");
        }
        String value = body.get("value");
        configStore.set(key, value);
        return Map.of("key", key, "newValue", value);
    }
}
