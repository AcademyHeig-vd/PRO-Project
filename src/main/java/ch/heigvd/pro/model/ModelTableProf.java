package ch.heigvd.pro.model;

import ch.heigvd.pro.connexion.dbConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModelTableProf {
    String acronyme;
    String nom;
    String prenom;
    String mail;
    String oldAcronyme;

    public ModelTableProf(String acronyme, String nom, String prenom, String mail) {
        this.acronyme = acronyme;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.oldAcronyme=acronyme;
    }

    public static boolean findByAcro(String acronyme) throws SQLException, ClassNotFoundException {
        ArrayList<ModelTableProf> profs = new ArrayList<>();
        dbConnexion db = new dbConnexion();
        Connection conn = db.obtConnexion();


        PreparedStatement stmt = null;
        stmt = conn.prepareStatement(dbConnexion.REQUETE_SELECTION_ACRONYME_UN_PROF);
        stmt.setString(1, acronyme);
        stmt.execute();
        ResultSet rs =stmt.getResultSet();
        return  rs != null;

    }
    /**
     * Supprime le prof de la base de donnée
     */
    public static boolean deleteFromDB(String acro)  {
        try{
        // Connexion a la database
        dbConnexion db = new dbConnexion();
        Connection connection = db.obtConnexion();

        PreparedStatement stmt = null;
        stmt = connection.prepareStatement(dbConnexion.REQUETE_SUPPRESSION_PROF);
        stmt.setString(1, acro);
        stmt.execute();
        return  true;
    }catch (Exception e){
        return  false;
    }

    }

    public boolean updateFromDB() {
       try{
            dbConnexion db = new dbConnexion();
            Connection connection = db.obtConnexion();

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(dbConnexion.REQUETE_MAJ_PROF);
            preparedStatement.setString(1, acronyme);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setString(4, mail);
            preparedStatement.setString(5, oldAcronyme);
            preparedStatement.execute();
            return deleteFromDB(oldAcronyme);

        }catch (Exception e){
            return  false;
        }
        }

    /**
     * Récupère la liste de tous les profs
     * @return ArrayList de prof
     * @throws SQLException Erreur lors de la requête
     * @throws ClassNotFoundException classe non trouvée
     */
    public static ArrayList<ModelTableProf> selectAllProfFromDB() throws SQLException, ClassNotFoundException {
        ArrayList<ModelTableProf> profs = new ArrayList<>();
        dbConnexion db = new dbConnexion();
        Connection conn = db.obtConnexion();

        ResultSet rs = conn.createStatement().executeQuery(dbConnexion.REQUETE_SELECTION_TOUS_PROFS);

        while(rs.next()){
            profs.add(new ModelTableProf(rs.getString("acronyme"), rs.getString("nom"), rs.getString("prenom"),
                    rs.getString("mail")));
        }
        return profs;
    }

    /**
     * Insertion d'un enseignant dans la base de donnée
     * @param acronyme acronyme
     * @param nom nom
     * @param prenom prenom
     * @param mail mail
     * @return vrai si l'intertion a réussi
     */
    public static boolean insertProfInDB(String acronyme, String nom, String prenom, String mail){

        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try {
            dbConnexion db = new dbConnexion();
            Connection connection = db.obtConnexion();

             // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(dbConnexion.REQUETE_INSERTION_PROF);
            preparedStatement.setString(1, acronyme);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setString(4, mail);

            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            // print SQL exception information
            dbConnexion.afficheExceptionSQL(e);
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getAcronyme() {
        return acronyme;
    }

    public void setAcronyme(String acronyme)  {
       // boolean res=findByAcro(acronyme);
       // if(res) {
            this.acronyme = acronyme;
       // }
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setOldAcronyme(String oldAcronyme) {
        this.oldAcronyme = oldAcronyme;
    }
}
