Feature: Validar o campo email

  Scenario Outline: Validar o campo email
    Given informe o request "<dados>"
    When tentar enviar o email
    Then ocorre um erro <statusCode>

    Examples:
      | dados            | statusCode |
      | Teste,email      | 400        |
      | Teste,email@     | 400        |
      | Teste,email.com  | 400        |
      | Teste,email@.com | 400        |
      | Teste,null       | 400        |
