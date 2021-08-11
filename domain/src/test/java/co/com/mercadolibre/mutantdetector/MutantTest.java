package co.com.mercadolibre.mutantdetector;

import co.com.mercadolibre.mutantdetector.exception.IncompleteDNAException;
import co.com.mercadolibre.mutantdetector.exception.InvalidDNACodeException;
import co.com.mercadolibre.mutantdetector.ports.MutantPersistencePort;
import co.com.mercadolibre.mutantdetector.service.MutantServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class MutantTest {

    @InjectMocks
    private MutantServiceImpl mutantServicePort;

    @Mock
    private MutantPersistencePort mutantPersistencePort;

    @Test
    public void isMutant() throws IncompleteDNAException, InvalidDNACodeException {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};

        doNothing().when(mutantPersistencePort).saveMutant(dna, true);

        Assert.assertTrue("The dna provided must belong to a mutant",
                mutantServicePort.isMutant(dna));
    }

    @Test
    public void isHuman() throws IncompleteDNAException, InvalidDNACodeException {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};

        doNothing().when(mutantPersistencePort).saveMutant(dna, false);

        Assert.assertFalse("The dna provided must belong to a human",
                mutantServicePort.isMutant(dna));
    }

    @Test
    public void isHumanOnlyOneSequence() throws IncompleteDNAException, InvalidDNACodeException {
        String[] dna = {"AAAAGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};

        doNothing().when(mutantPersistencePort).saveMutant(dna, false);

        Assert.assertFalse("The dna provided must belong to a human",
                mutantServicePort.isMutant(dna));
    }

    @Test
    public void isMutant6x6TwoVertical() throws IncompleteDNAException, InvalidDNACodeException {
        String[] dna = {"TACCCT", "TTAACA", "TGGTGT", "TCTGGT", "GGTGGA", "ACTTGC"};

        doNothing().when(mutantPersistencePort).saveMutant(dna, true);

        Assert.assertTrue("The dna provided must belong to a mutant",
                mutantServicePort.isMutant(dna));
    }

    @Test
    public void isMutant6x6TwoHorizontal() throws IncompleteDNAException, InvalidDNACodeException {
        String[] dna = {"CCCCGT", "TTAACA", "TGGTGT", "TCTTTT", "GGTGGA", "ACTTGC"};

        doNothing().when(mutantPersistencePort).saveMutant(dna, true);

        Assert.assertTrue("The dna provided must belong to a mutant",
                mutantServicePort.isMutant(dna));
    }

    @Test
    public void isMutant6x6TwoObliqueLeft() throws IncompleteDNAException, InvalidDNACodeException {
        String[] dna = {"CCCTGT", "TTTACA", "TTGTAG", "TCTTGT", "GGTGGA", "ACGTGC"};

        doNothing().when(mutantPersistencePort).saveMutant(dna, true);

        Assert.assertTrue("The dna provided must belong to a mutant",
                mutantServicePort.isMutant(dna));
    }

    @Test
    public void isMutant6x6TwoObliqueRight() throws IncompleteDNAException, InvalidDNACodeException {
        String[] dna = {"ACGTGA", "TATGAA", "TGAAGG", "TCTAGG", "GGTATA", "ACGTGC"};

        doNothing().when(mutantPersistencePort).saveMutant(dna, true);

        Assert.assertTrue("The dna provided must belong to a mutant",
                mutantServicePort.isMutant(dna));
    }

    @Test
    public void isMutant4x4() throws IncompleteDNAException, InvalidDNACodeException {
        String[] dna = {"ATGC", "ATGC", "ATGT", "ATAG"};

        doNothing().when(mutantPersistencePort).saveMutant(dna, true);

        Assert.assertTrue("The dna provided must belong to a mutant",
                mutantServicePort.isMutant(dna));
    }

    @Test
    public void isMutant5x5() throws IncompleteDNAException, InvalidDNACodeException {
        String[] dna = {"ATGAC", "ATGCA", "ATGTA", "ATAGA", "CCATG"};

        doNothing().when(mutantPersistencePort).saveMutant(dna, true);

        Assert.assertTrue("The dna provided must belong to a mutant",
                mutantServicePort.isMutant(dna));
    }

    @Test
    public void isMutant12x12() throws IncompleteDNAException, InvalidDNACodeException {
        String[] dna = {"GCCCCTACGTGA", "GGAACATCCATA", "TGGTTTTACACA", "TCTGATTACGAG", "GGTGGATTCTCG", "ACTTGCCGCTTA",
                "ACTCCTACCCCC", "GCAACGCGGCTC", "TGCCTGTCAACT", "TGTATCCGTTTC", "GTGTGGACTTCC", "GTGTGAGCCGTC"};

        doNothing().when(mutantPersistencePort).saveMutant(dna, true);

        Assert.assertTrue("The dna provided must belong to a mutant",
                mutantServicePort.isMutant(dna));
    }

    @Test(expected = InvalidDNACodeException.class)
    public void invalidDNA() throws IncompleteDNAException, InvalidDNACodeException {
        mutantServicePort.isMutant(new String[] {"XTGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"});
    }

    @Test(expected = IncompleteDNAException.class)
    public void incompleteDNA() throws IncompleteDNAException, InvalidDNACodeException {
        mutantServicePort.isMutant(new String[]{"CAGTC", "TTATT", "AGACGG", "GCGTCA", "TCACTG"});
    }

    @Test(expected = NullPointerException.class)
    public void nullDNA() throws IncompleteDNAException, InvalidDNACodeException {
        mutantServicePort.isMutant(null);
    }
}
