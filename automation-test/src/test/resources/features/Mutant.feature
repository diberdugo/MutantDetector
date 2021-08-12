Feature: This feature verifies if a given DNA is mutant or human


  Scenario Outline: Validate mutant DNA
    Given I configured the mutants API
    When I want to validate if the "<dna>" DNA is a mutant
    Then I will receipt HTTP code <statusCode> as service response

    Examples:
      | dna                                                                                                                                                         | statusCode |
      | ATGAC,ATGCA,ATGTA,ATAGA,CCATG                                                                                                                               | 200        |
      | ATGAC,ATGCA,ATGTA,ATAGA,CCATG                                                                                                                               | 200        |
      | ATGCGA,CAGTGC,TTATTT,AGACGG,GCGTCA,TCACTG                                                                                                                   | 403        |
      | AAAAGA,CAGTGC,TTATTT,AGACGG,GCGTCA,TCACTG                                                                                                                   | 403        |
      | TACCCT,TTAACA,TGGTGT,TCTGGT,GGTGGA,ACTTGC                                                                                                                   | 200        |
      | ACGTGA,TATGAA,TGAAGG,TCTAGG,GGTATA,ACGTGC                                                                                                                   | 200        |
      | CCCTGT,TTTACA,TTGTAG,TCTTGT,GGTGGA,ACGTGC                                                                                                                   | 200        |
      | CCCCGT,TTAACA,TGGTGT,TCTTTT,GGTGGA,ACTTGC                                                                                                                   | 200        |
      | TACCCT,TTAACA,TGGTGT,TCTGGT,GGTGGA,ACTTGC                                                                                                                   | 200        |
      | TACCCT,TTAACA,TGGTGT,TCTGGT,GGTGGA,ACTTGC                                                                                                                   | 403        |
      | GCCCCTACGTGA,GGAACATCCATA,TGGTTTTACACA,TCTGATTACGAG,GGTGGATTCTCG,ACTTGCCGCTTA,ACTCCTACCCCC,GCAACGCGGCTC,TGCCTGTCAACT,TGTATCCGTTTC,GTGTGGACTTCC,GTGTGAGCCGTC | 200        |


  Scenario Outline: Validate invalid mutant DNA
    Given I configured the mutants API
    When I want to validate if the "<dna>" DNA is a mutant
    Then I will receipt HTTP code <statusCode> as service response

    Examples:
      | dna                                       | statusCode |
      | XTGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG | 400        |
      | CAGTC,TTATT,AGACGG,GCGTCA,TCACTG          | 400        |
      | null                                      | 400        |
