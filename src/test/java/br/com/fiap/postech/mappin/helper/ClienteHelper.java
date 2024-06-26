package br.com.fiap.postech.mappin.helper;

import br.com.fiap.postech.mappin.model.Cliente;
import br.com.fiap.postech.mappin.model.Endereco;

import java.util.UUID;

public class ClienteHelper {

    private static class GeraCpfCnpj {

        private static int randomiza(int n) {
            int ranNum = (int) (Math.random() * n);
            return ranNum;
        }

        private static int mod(int dividendo, int divisor) {
            return (int) Math.round(dividendo - (Math.floor(dividendo / divisor) * divisor));
        }

        public static String cpf() {
            int n = 9;
            int n1 = randomiza(n);
            int n2 = randomiza(n);
            int n3 = randomiza(n);
            int n4 = randomiza(n);
            int n5 = randomiza(n);
            int n6 = randomiza(n);
            int n7 = randomiza(n);
            int n8 = randomiza(n);
            int n9 = randomiza(n);
            int d1 = n9 * 2 + n8 * 3 + n7 * 4 + n6 * 5 + n5 * 6 + n4 * 7 + n3 * 8 + n2 * 9 + n1 * 10;

            d1 = 11 - (mod(d1, 11));

            if (d1 >= 10)
                d1 = 0;

            int d2 = d1 * 2 + n9 * 3 + n8 * 4 + n7 * 5 + n6 * 6 + n5 * 7 + n4 * 8 + n3 * 9 + n2 * 10 + n1 * 11;

            d2 = 11 - (mod(d2, 11));


            if (d2 >= 10)
                d2 = 0;
            return "" + n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + d1 + d2;
        }
    }

    public static Cliente getCliente(boolean geraId) {
        return getCliente(geraId, null);
    }
    public static Cliente getCliente(boolean geraId, String idEndereco) {
        UUID internalIdEndereco;
        if (idEndereco != null) {
            internalIdEndereco = UUID.fromString(idEndereco);
        } else {
            internalIdEndereco = null;
        }
        var endereco = new Endereco(internalIdEndereco,"Rua do oriente", "283", "12345678", "São Paulo/SP/Brasil");
        UUID idCliente = null;
        if (geraId) {
            idCliente = UUID.randomUUID();
        }
        return new Cliente(
                idCliente,
                "Anderson Wagner",
                GeraCpfCnpj.cpf(),
                endereco
        );
    }
}
