Feature: Validar o campo name

  Scenario Outline: Validar o campo name
    Given informe o request "<dados>"
    When tentar enviar o email
    Then ocorre um erro <statusCode>

    Examples:
      | dados                                                                                 | statusCode |
      | sda,GENERATE                                                                          | 400        |
      | sddsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,GENERATE | 400        |
      | null,GENERATE                                                                         | 400        |
