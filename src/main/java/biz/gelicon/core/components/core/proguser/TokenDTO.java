package biz.gelicon.core.components.core.proguser;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Объект для передачи токена аутентификации")
public class TokenDTO {
    @Schema(description = "Токен аутентификации")
    private String token;

    public TokenDTO() {
    }

    public TokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
