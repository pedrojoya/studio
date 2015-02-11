package es.iessaladillo.pedrojoya.pr113.Autenticacion;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

public class Autenticador extends AbstractAccountAuthenticator {

    // Constantes.
    public static final String ACCOUNT_TYPE = "com.udinic.auth_example";
    public static final String ACCOUNT_NAME = "Udinic";
    public static final String AUTHTOKEN_TYPE_READ_ONLY = "Read only";
    public static final String AUTHTOKEN_TYPE_READ_ONLY_LABEL = "Read only access to an Udinic account";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "Full access";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS_LABEL = "Full access to an Udinic account";

    private final Context mContext;

    // Constructor.
    public Autenticador(Context context) {
        // Debemos llamar obligatoriamente al constructor del padre.
        super(context);
        this.mContext = context;
    }

    // Cuando se quiere añadir una nueva cuenta al gestor de cuentas.
    @Override
    public Bundle addAccount(
            AccountAuthenticatorResponse response, // objeto para enviar la respuesta al gestor de cuentas
            String accountType,     // nombre de la cuenta
            String authTokenType,   // tipo de token de autenticación a obtener tras añadir la cuenta.
            String[] requiredFeatures,
            Bundle options) throws NetworkErrorException {

        // Quien realmente añade la cuenta al gestor es la actividad de autenticación, por lo que
        // tenemos que llamarla pasándole la información que necesita: el nombre de la cuenta, el tipo
        // de token de autenticación, un flag indicativo que que se quiere es añadir una cuenta y
        // el objeto a través del que enviar la respuesta al gestor.
        final Intent intent = new Intent(mContext, AutenticacionActivity.class);
        intent.putExtra(AutenticacionActivity.ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(AutenticacionActivity.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(AutenticacionActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        // Se retorna un bundle con un parcelable correspondiente al intent recién creado.
        // (la clase Intent implementa Parcelable)
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    // Cuando se solicita un token de autenticación.
    @Override
    public Bundle getAuthToken(
            AccountAuthenticatorResponse response, // objeto para enviar la respuesta al gestor de cuentas
            Account account,    // Cuenta de la que se quiere obtener el token.
            String authTokenType,   // tipo de token de autenticación a obtener.
            Bundle options) throws NetworkErrorException {

        // Si el tipo de token de autenticación no es soportado por el autenticador, se retorna un bundle
        // con el error.
        if (!authTokenType.equals(AUTHTOKEN_TYPE_READ_ONLY) &&
                !authTokenType.equals(AUTHTOKEN_TYPE_FULL_ACCESS)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType");
            return result;
        }

        // Obtenemos el gestor de cuentas y de él el token de autenticación almacenado.
        final AccountManager am = AccountManager.get(mContext);
        String authToken = am.peekAuthToken(account, authTokenType);

/*
        // Si no tenemos el token, tratamos de reautenticarnos en el servicio web
        // con las credenciales ya almacenadas (si las hay).
        if (TextUtils.isEmpty(authToken)) {
            final String password = am.getPassword(account);
            if (password != null) {
                try {
                    Usuario usuario = App.getApiService().login(account.name, password);
                    authToken = usuario.getSessionToken();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
*/

        // Si tenemos el token, creamos un bundle con el nombre de la cuenta,
        // el tipo de cuenta y el token, y lo retornamos.
        if (!TextUtils.isEmpty(authToken)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }

        // Si seguimos sin tener el token es necesario que el usuario introduzca unas
        // nuevas credenciales, por lo que creamos un bundle que contenga un intent
        // para mostrar la actividad de autenticación, con los datos necesarios,
        // y retornamos el bundle.
        final Intent intent = new Intent(mContext, AutenticacionActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(AutenticacionActivity.ARG_ACCOUNT_TYPE, account.type);
        intent.putExtra(AutenticacionActivity.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(AutenticacionActivity.ARG_ACCOUNT_NAME, account.name);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    // Retorna la descripción de un tipo de token de autenticación.
    @Override
    public String getAuthTokenLabel(String authTokenType) {
        if (AUTHTOKEN_TYPE_FULL_ACCESS.equals(authTokenType))
            return AUTHTOKEN_TYPE_FULL_ACCESS_LABEL;
        else if (AUTHTOKEN_TYPE_READ_ONLY.equals(authTokenType))
            return AUTHTOKEN_TYPE_READ_ONLY_LABEL;
        else
            return authTokenType + " (Label)";
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        return null;
    }

}