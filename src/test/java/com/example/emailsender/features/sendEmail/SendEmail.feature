Feature: Validar o envio

  Scenario Outline: Validar sucesso ao enviar o email
    Given informe o request "<dados>"
    When tentar enviar o email
    Then ocorre um erro <statusCode>

    Examples:
      | dados          | statusCode |
      | Teste,GENERATE | 204        |