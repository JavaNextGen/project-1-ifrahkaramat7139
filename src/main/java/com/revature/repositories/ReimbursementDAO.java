package com.revature.repositories;

import com.revature.models.Reimbursement;
import com.revature.models.Role;
import com.revature.models.Status;
import com.revature.models.Type;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ReimbursementDAO {

    /**
     * Should retrieve a Reimbursement from the DB with the corresponding id or an empty optional if there is no match.
     */
    public Optional<Reimbursement> getById(int id) {
        return Optional.empty();
    }

    /**
     * Should retrieve a List of Reimbursements from the DB with the corresponding Status or an empty List if there are no matches.
     */
    public List<Reimbursement> getByStatus(Status status) {
        return Collections.emptyList();
    }

    /**
     * <ul>
     *     <li>Should Update an existing Reimbursement record in the DB with the provided information.</li>
     *     <li>Should throw an exception if the update is unsuccessful.</li>
     *     <li>Should return a Reimbursement object with updated information.</li>
     * </ul>
     */
    public Reimbursement update(Reimbursement unprocessedReimbursement) {
    	return null;
    }

	public int saveReimbursement(int eid, Reimbursement reimbursement) 
	{
		System.out.println("In DAO");
		try(Connection conn = ConnectionFactory.getConnection())
		{
			//we'll create a SQL statement using parameters to insert a new Reimbursement
			String sql = "INSERT INTO ers_reimbursement (reimb_amount, reimb_submitted, reimb_receipt, reimb_author, reimb_status_id, reimb_type_id, reimb_description) " //creating a line break for readability
					+ "VALUES (?, ?, ?,?,?,?, ?); "; //these are parameters!! We have to specify the value of each "?"

			PreparedStatement pst = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS); //we use PreparedStatements for SQL commands with variables

			//use the PreparedStatement objects' methods to insert values into the query's ?s
			//the values will come from the Reimbursement object we send in.
			pst.setDouble(1, reimbursement.getAmount()); //1 is the first ?, 2 is the second, etc.
			pst.setDate(2, new java.sql.Date(new Date().getTime()));
			pst.setBytes(3, reimbursement.getReceipt()); //1 is the first ?, 2 is the second, etc.
			pst.setInt(4, eid);
			pst.setInt(5, 1);
			if (reimbursement.getType().toString().equalsIgnoreCase("lodging")) {
				pst.setInt(6, 1);
			}
			else if(reimbursement.getType().toString().equalsIgnoreCase("travel")) {
				pst.setInt(6, 2);
			}
			else if(reimbursement.getType().toString().equalsIgnoreCase("food")) {
				pst.setInt(6, 3);
			}
			else
				pst.setInt(6, 4);
			pst.setString(7, reimbursement.getDescription());


			System.out.println("Executing insert query " + pst.executeUpdate()); //we use executeUpdate() for inserts, updates, and deletes. 
			//we use executeQuery() for selects

			//send confirmation to the console if successful.
			
			try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int id=generatedKeys.getInt(1);
					System.out.println("Reimbursement " + id + " created for Employee " + eid + "!");
					return id;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		} catch(SQLException e) {
			System.out.println("Submitting Reimbursement failed! :(");
			e.printStackTrace();
		}
		return -1;
	}
	
	public List<Reimbursement> getAllReimbursementsFromEmpId(int eid) 
	{
		try(Connection conn = ConnectionFactory.getConnection())
		{
			Statement selectStmt = conn.createStatement();
			String query = "SELECT * from ers_reimbursement where reimb_author = " + eid;

			List<Reimbursement> reimbursements = new ArrayList<>();
			ResultSet rs = selectStmt
					.executeQuery(query);
			while(rs.next())
			{
				Reimbursement reimbursement = getReimbursementObject(rs);
				reimbursements.add(reimbursement);

			}
			return reimbursements;
		}
		catch(SQLException e) 
		{
			System.out.println("error !!?");
			e.printStackTrace();
		}
		return null;
		
	}
    
	public Reimbursement getReimbursementFromEmpId(int eid, int rid) {
		try(Connection conn = ConnectionFactory.getConnection())
		{
			Statement selectStmt = conn.createStatement();
			String query = "SELECT * from ers_reimbursement where reimb_author = " + eid + " and reimb_id = " + rid;

			Reimbursement reimbursement = null;
			ResultSet rs = selectStmt
					.executeQuery(query);
			
			while(rs.next())
			{
				reimbursement = getReimbursementObject(rs);
				return reimbursement;

			}
		}
		catch(SQLException e) 
		{
			System.out.println("error !!?");
			e.printStackTrace();
		}
		return null;
	}
	
	public int deleteReimbursementFromEmpId(int eid, int rid) 
	{
		try(Connection conn = ConnectionFactory.getConnection())
		{

			String query = "Delete from project1.ers_reimbursement r "
					+ "using project1.ers_reimbursement_status s where reimb_author = ? "
					+ "and reimb_id = ? "
					+ "and r.reimb_status_id = s.reimb_status_id "
					+ "and s.reimb_status != 'APPROVED';"; 
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, eid);
			pst.setInt(2, rid);

			int result = pst.executeUpdate();
			return result;
		}
		catch(SQLException e) 
		{
			System.out.println("error !!?");
			e.printStackTrace();
		}
		return -1;
	}
	
	public int updateReimbursement(int eid, int rid, Reimbursement reimbursement) 
	{
		try(Connection conn = ConnectionFactory.getConnection())
		{

			String query = "Update ers_reimbursement r "
					+ "set reimb_amount = ?, "
					+ "reimb_description = ?, "
					+ "reimb_receipt = ?, " 
					+ "reimb_type_id = ?, "
					+ "reimb_status_id = ? "
					+ "from ers_reimbursement_status s "
					+ "where reimb_author = ? "
					+ "and reimb_id = ? "
					+ "and r.reimb_status_id = s.reimb_status_id "
					+ "and s.reimb_status != 'APPROVED';"; 
			
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setDouble(1, reimbursement.getAmount());
			pst.setString(2, reimbursement.getDescription());
			pst.setBytes(3, reimbursement.getReceipt());
			if (reimbursement.getType().toString().equalsIgnoreCase("lodging")) {
				pst.setInt(4, 1);
			}
			else if(reimbursement.getType().toString().equalsIgnoreCase("travel")) {
				pst.setInt(4, 2);
			}
			else if(reimbursement.getType().toString().equalsIgnoreCase("food")) {
				pst.setInt(4, 3);
			}
			else
				pst.setInt(4, 4);
			pst.setInt(5, 1);
			pst.setInt(6, eid);
			pst.setInt(7, rid);

			int result = pst.executeUpdate();
			return result;
		}
		catch(SQLException e) 
		{
			System.out.println("error !!?");
			e.printStackTrace();
		}
		return -1;
	}	
	
    // utility function
 	private Reimbursement getReimbursementObject(ResultSet rs) throws SQLException
 	{
 		Reimbursement reimbursement = new Reimbursement();
 		
 		reimbursement.setId(rs.getInt("reimb_id"));
 		reimbursement.setAmount(rs.getDouble("reimb_amount"));
 		reimbursement.setSubmitDate(rs.getDate("reimb_submitted"));
 		reimbursement.setResolvedDate(rs.getDate("reimb_resolved"));
 		reimbursement.setReceipt(rs.getBytes("reimb_receipt"));
 		reimbursement.setAuthor(rs.getInt("reimb_author"));
 		reimbursement.setResolver(rs.getInt("reimb_resolver")==0?null:rs.getInt("reimb_resolver"));
 		reimbursement.setStatus(Status.values()[rs.getInt("reimb_status_id")-1]);
 		reimbursement.setType(Type.values()[rs.getInt("reimb_type_id")-1]);
 		reimbursement.setDescription(rs.getString("reimb_description"));
 	
 		return reimbursement;
 	}

	
    
}
