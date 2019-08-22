package edu.miami.ccs.life;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * Servlet implementation class to fetch Participant summary
 * 
 * @author A Koleti, UM Center for Computational Science
 * @version 1.0
 */
public class FetchSummary extends HttpServlet {
	/**
	 * Constant serialized ID used for compatibility.
	 */
	private static final long serialVersionUID = 1289131897014606851L;
	Logger log = Logger.getLogger(this.getClass().getName());
	SessionFactory sessions;
	Session ses;

	/**
	 * Overrides the method in GenericServlet class to create an active
	 * hibernate session
	 */
	// end of method
	public void init(ServletConfig config) throws ServletException {

		sessions = connect();
		ses = sessions.openSession();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		String input = request.getParameter("input");
		String mode = request.getParameter("mode");
		String source;
		if(request.getParameter("source").equals("null")){
			source = "LIFE";
		}else {
			source = request.getParameter("source");
		}

		String json = "";
		if (mode.equalsIgnoreCase("SmallMolecule")) {
			String QueryStr = " select s.smallmoleculename,s.smile,s.molecularweight,s.molecularformulae,s.perturbagenid as Lincs_CompoundId,s.pubchemid, "
					+ " s.smallmoleculeid,s.lincs_id,s.iupac_name,cc.allcategories,cc.pdbligandcode, "
					+ " cc.pdbcode,cc.category_npc,cc.category_drug,cc.drugbankid,cc.wikipediaurl,cc.category_probe,cc.probeurl,cc.category_kinaseinhibitortype, "
					+ "  cc.category_kinaseinhibitorpromiscuity,cc.category_kinaseinhibitor,ss.tpsa,ss.logp,ss.logd,ss.rotatable_bonds, "
					+ "  ss.hba,ss.hbd,ss.lipinski_r5,ss.lipinski_r3,ss.lead_likeness,ss.bioavailability,s.compound_salt "
				 + " from smallmolecule s  "
                  + "   inner join participant_to_sourceid ps on (ps.lifeid = s.smallmoleculeid) "
				+ " left outer join compound_categories cc on (cc.lincs_id = s.lincs_id) "
				+ " left outer join substance_summary ss on (ss.lincs_id = s.lincs_id)  "
				+ " where ps.mode = 'SmallMolecule' " + "and ps.source = '"+source.trim()+"' and  ps.sourceid = '"+input.trim()+"' ";

			SQLQuery query = ses.createSQLQuery(QueryStr)
					.addScalar("smallmoleculename", Hibernate.STRING)
					.addScalar("smile", Hibernate.STRING)
					.addScalar("molecularweight", Hibernate.STRING)
					.addScalar("molecularformulae", Hibernate.STRING)
					.addScalar("Lincs_CompoundId", Hibernate.STRING)
					.addScalar("pubchemid", Hibernate.STRING)
					.addScalar("smallmoleculeid", Hibernate.STRING)
					
					.addScalar("lincs_id", Hibernate.STRING)
					.addScalar("iupac_name", Hibernate.STRING)
					.addScalar("allcategories", Hibernate.STRING)
					.addScalar("pdbligandcode", Hibernate.STRING)
					.addScalar("pdbcode", Hibernate.STRING)
					.addScalar("category_npc", Hibernate.STRING)
					.addScalar("category_drug", Hibernate.STRING)
					
					.addScalar("drugbankid", Hibernate.STRING)
					.addScalar("wikipediaurl", Hibernate.STRING)
					.addScalar("category_probe", Hibernate.STRING)
					.addScalar("probeurl", Hibernate.STRING)
					.addScalar("category_kinaseinhibitortype", Hibernate.STRING)
					.addScalar("category_kinaseinhibitorpromiscuity", Hibernate.STRING)
					.addScalar("category_kinaseinhibitor", Hibernate.STRING)
					
					.addScalar("tpsa", Hibernate.FLOAT)
					.addScalar("logp", Hibernate.FLOAT)
					.addScalar("logd", Hibernate.FLOAT)
					.addScalar("rotatable_bonds", Hibernate.INTEGER)
					.addScalar("hba", Hibernate.INTEGER)
					.addScalar("hbd", Hibernate.INTEGER)
					.addScalar("lipinski_r5", Hibernate.STRING)
					
					.addScalar("lipinski_r3", Hibernate.STRING)
					.addScalar("lead_likeness", Hibernate.STRING)
					.addScalar("bioavailability", Hibernate.STRING)
					.addScalar("compound_salt", Hibernate.STRING);

			Iterator objects = null;
			try {
				objects = query.list().iterator();
			} catch (HibernateException he) {
				log.info("Possibly a communications link failure, check database connectivity");
				out.print("Error retrieving data, please try again");
				sessions.close();
				return;
			}
			
			if (objects.hasNext()) {

				Object[] tuple = (Object[]) objects.next();
				try {
					jsonObj.put("smallmoleculename", (String) tuple[0]);
					jsonObj.put("smile", (String) tuple[1]);
					jsonObj.put("molecularweight", (String) tuple[2]);
					jsonObj.put("molecularformulae", (String) tuple[3]);
					jsonObj.put("Lincs_CompoundId", (String) tuple[4]);
					jsonObj.put("pubchemid", (String) tuple[5]);
					jsonObj.put("solrParticipantId", (String) tuple[6]);
					
					   jsonObj.put("lincs_id", (String) tuple[7]);
					   jsonObj.put("iupac_name", (String) tuple[8]);
					   jsonObj.put("allcategories", (String) tuple[9]);
					   jsonObj.put("pdbligandcode", (String) tuple[10]);
					   jsonObj.put("pdbcode", (String) tuple[11]);
					   jsonObj.put("category_npc", (String) tuple[12]);
					   jsonObj.put("category_drug", (String) tuple[13]);
					
					   jsonObj.put("drugbankid", (String) tuple[14]);
					   jsonObj.put("wikipediaurl", (String) tuple[15]);
					   jsonObj.put("category_probe", (String) tuple[16]);
					   jsonObj.put("probeurl", (String) tuple[17]);
					   jsonObj.put("category_kinaseinhibitortype", (String) tuple[18]);
					   jsonObj.put("category_kinaseinhibitorpromiscuity", (String) tuple[19]);
					   jsonObj.put("category_kinaseinhibitor", (String) tuple[20]);
					
					   jsonObj.put("tpsa", (Float) tuple[21]);
					   jsonObj.put("logp", (Float) tuple[22]);
					   jsonObj.put("logd", (Float) tuple[23]);
					   jsonObj.put("rotatable_bonds", (Integer) tuple[24]);
					   jsonObj.put("hba", (Integer) tuple[25]);
					   jsonObj.put("hbd", (Integer) tuple[26]);
					   jsonObj.put("lipinski_r5", (String) tuple[27]);
					
					   jsonObj.put("lipinski_r3", (String) tuple[28]);
					   jsonObj.put("lead_likeness", (String) tuple[29]);
					   jsonObj.put("bioavailability", (String) tuple[30]);
					   jsonObj.put("salt_id", (String) tuple[31]);
					
					out.print(jsonObj.toString());
				}

				catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}
		if (mode.equalsIgnoreCase("Protein")) {
			String QueryStr = "select p.proteinname,p.sequence,p.mutation,p.modification,p.labelingsite,p.organism,p.uniprotid, "
					 +" p.proteinid,pf.kinasedomaincategory1  as kinase_domain,pf.proteinname as kinasedescription,pf.kinasesymbol as kinasesymbol, "
					+"  pf.kinasedomaincategory2 as kinasedomain,pf.proteinname as kinasename,pf.kinasegroup as kb_group,pf.kinasefamily as kb_family, "
					+"  pf.gatekeeper as gatekeeper,pf.hinge_i1 as hinge_i1,pf.hinge_i3 as hinge_i3, p.source_id "
					+"  from protein p "
                    +"     inner join participant_to_sourceid ps on (ps.lifeid = p.proteinid) "
					+"    left outer join proteins_class pf on (pf.kinomescanannotationname = p.proteinname) "
                    +" where ps.mode = 'Protein' " + "and ps.source = '"+source.trim()+"' and  ps.sourceid = '"+input.trim()+"'";

			SQLQuery query = ses.createSQLQuery(QueryStr)
					.addScalar("proteinname", Hibernate.STRING)
					.addScalar("sequence", Hibernate.STRING)
					.addScalar("mutation", Hibernate.STRING)
					.addScalar("modification", Hibernate.STRING)
					.addScalar("labelingsite", Hibernate.STRING)
					.addScalar("organism", Hibernate.STRING)
					.addScalar("uniprotid", Hibernate.STRING)
					.addScalar("proteinid", Hibernate.STRING)

					.addScalar("kinase_domain", Hibernate.STRING)
					.addScalar("kinasedescription", Hibernate.STRING)
					.addScalar("kinasesymbol", Hibernate.STRING)
					.addScalar("kinasedomain", Hibernate.STRING)
					.addScalar("kinasename", Hibernate.STRING)
					.addScalar("kb_group", Hibernate.STRING)
					.addScalar("kb_family", Hibernate.STRING)

					.addScalar("gatekeeper", Hibernate.STRING)
					.addScalar("hinge_i1", Hibernate.STRING)
					.addScalar("hinge_i3", Hibernate.STRING)
					.addScalar("source_id", Hibernate.STRING);

			Iterator objects = null;
			try {
				objects = query.list().iterator();
			} catch (HibernateException he) {
				log.info("Possibly a communications link failure, check database connectivity");
				out.print("Error retrieving data, please try again");
				sessions.close();
				return;
			}
			String[] arr = new String[6];
			if (objects.hasNext()) {

				Object[] tuple = (Object[]) objects.next();
				try {
					jsonObj.put("proteinname", (String) tuple[0]);
					jsonObj.put("sequence", (String) tuple[1]);
					jsonObj.put("mutation", (String) tuple[2]);
					jsonObj.put("modification", (String) tuple[3]);
					jsonObj.put("labelingsite", (String) tuple[4]);
					jsonObj.put("organism", (String) tuple[5]);
					jsonObj.put("uniprotid", (String) tuple[6]);
					jsonObj.put("solrParticipantId", (String) tuple[7]);

					jsonObj.put("Spkinasedomain", (String) tuple[8]);
					jsonObj.put("kinasedescription", (String) tuple[9]);
					jsonObj.put("kinasesymbol", (String) tuple[10]);
					jsonObj.put("kinasedomain", (String) tuple[11]);
					jsonObj.put("kinasename", (String) tuple[12]);
					jsonObj.put("kinasegroup", (String) tuple[13]);
					jsonObj.put("kinasefamily", (String) tuple[14]);
					jsonObj.put("sourceid", (String) tuple[18]);
					if (!tuple[2].toString().equals("")) {
						jsonObj.put("gatekeeper", "");
						jsonObj.put("kinasehingei1", "");
						jsonObj.put("kinasehingei3", "");
						
					}else
					{
						jsonObj.put("gatekeeper", (String) tuple[15]);
						jsonObj.put("kinasehingei1", (String) tuple[16]);
						jsonObj.put("kinasehingei3", (String) tuple[17]);
					}

					out.print(jsonObj.toString());
				}

				catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}
		if (mode.equalsIgnoreCase("PhosphoProtein")) {
			String QueryStr = "select id,uniprotid,geneid,genesymbol,basepeptide,cluster,clustercode,modifiedpeptide,probeid,phosphosite from PhosphoProteins where id ='"+input.trim()+"'";

			SQLQuery query = ses.createSQLQuery(QueryStr)
					.addScalar("id", Hibernate.STRING)
					.addScalar("uniprotid", Hibernate.STRING)
					.addScalar("geneid", Hibernate.STRING)
					.addScalar("genesymbol", Hibernate.STRING)
					.addScalar("basepeptide", Hibernate.STRING)
					.addScalar("cluster", Hibernate.STRING)
					.addScalar("clustercode", Hibernate.STRING)
					.addScalar("modifiedpeptide", Hibernate.STRING)
					.addScalar("probeid", Hibernate.STRING)
					.addScalar("phosphosite", Hibernate.STRING);

			Iterator objects = null;
			try {
				objects = query.list().iterator();
			} catch (HibernateException he) {
				log.info("Possibly a communications link failure, check database connectivity");
				out.print("Error retrieving data, please try again");
				sessions.close();
				return;
			}
			String[] arr = new String[6];
			if (objects.hasNext()) {

				Object[] tuple = (Object[]) objects.next();
				try {
					jsonObj.put("solrParticipantId", (String) tuple[0]);
					jsonObj.put("uniprotid", (String) tuple[1]);
					jsonObj.put("geneid", (String) tuple[2]);
					jsonObj.put("name", (String) tuple[3]);
					jsonObj.put("basepeptide", (String) tuple[4]);
					jsonObj.put("cluster", (String) tuple[5]);
					jsonObj.put("clustercode", (String) tuple[6]);
					

					jsonObj.put("modifiedpeptide", (String) tuple[7]);
					jsonObj.put("probeid", (String) tuple[8]);
					jsonObj.put("phosphosite", (String) tuple[9]);
				

					out.print(jsonObj.toString());
				}

				catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}
		if (mode.equalsIgnoreCase("Antibody")) {
			String QueryStr = "select pmproteinid as solr_participantid,pmname,pmorganism,pmuniprot,kdes,ksym,refseqid from antibodies where pmproteinid  ='"+input.trim()+"'";

			SQLQuery query = ses.createSQLQuery(QueryStr)
					.addScalar("solr_participantid", Hibernate.STRING)
					.addScalar("pmname", Hibernate.STRING)
					.addScalar("pmorganism", Hibernate.STRING)
					.addScalar("pmuniprot", Hibernate.STRING)
					.addScalar("kdes", Hibernate.STRING)
					.addScalar("ksym", Hibernate.STRING)
					.addScalar("refseqid", Hibernate.STRING);

			Iterator objects = null;
			try {
				objects = query.list().iterator();
			} catch (HibernateException he) {
				log.info("Possibly a communications link failure, check database connectivity");
				out.print("Error retrieving data, please try again");
				sessions.close();
				return;
			}
			String[] arr = new String[6];
			if (objects.hasNext()) {

				Object[] tuple = (Object[]) objects.next();
				try {
					jsonObj.put("solrParticipantId", (String) tuple[0]);
					jsonObj.put("name", (String) tuple[1]);
					jsonObj.put("organism", (String) tuple[2]);
					jsonObj.put("uniprot", (String) tuple[3]);
					jsonObj.put("description", (String) tuple[4]);
					jsonObj.put("symbol", (String) tuple[5]);
					jsonObj.put("refseqid", (String) tuple[6]);
				

					out.print(jsonObj.toString());
				}

				catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}
		if (mode.equalsIgnoreCase("ligand")) {
			String QueryStr = "select nonkinaseproteinid as solr_participantid,shortname,uniprotid,description from nonkinaseproteins where  nonkinaseproteinid ='"+input.trim()+"'";

			SQLQuery query = ses.createSQLQuery(QueryStr)
					.addScalar("solr_participantid", Hibernate.STRING)
					.addScalar("shortname", Hibernate.STRING)
					.addScalar("uniprotid", Hibernate.STRING)
					.addScalar("description", Hibernate.STRING);

			Iterator objects = null;
			try {
				objects = query.list().iterator();
			} catch (HibernateException he) {
				log.info("Possibly a communications link failure, check database connectivity");
				out.print("Error retrieving data, please try again");
				sessions.close();
				return;
			}
			String[] arr = new String[6];
			if (objects.hasNext()) {

				Object[] tuple = (Object[]) objects.next();
				try {
					jsonObj.put("solrParticipantId", (String) tuple[0]);
					jsonObj.put("name", (String) tuple[1]);
					jsonObj.put("uniprot", (String) tuple[2]);
					jsonObj.put("description", (String) tuple[3]);
				

					out.print(jsonObj.toString());
				}

				catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}
		
		if (mode.equalsIgnoreCase("NonKinaseProtein")) {
			String QueryStr = "select nonkinaseproteinid as solr_participantid,shortname,uniprotid,description from nonkinaseproteins where  nonkinaseproteinid ='"+input.trim()+"'";

			SQLQuery query = ses.createSQLQuery(QueryStr)
					.addScalar("solr_participantid", Hibernate.STRING)
					.addScalar("shortname", Hibernate.STRING)
					.addScalar("uniprotid", Hibernate.STRING)
					.addScalar("description", Hibernate.STRING);

			Iterator objects = null;
			try {
				objects = query.list().iterator();
			} catch (HibernateException he) {
				log.info("Possibly a communications link failure, check database connectivity");
				out.print("Error retrieving data, please try again");
				sessions.close();
				return;
			}
			String[] arr = new String[6];
			if (objects.hasNext()) {

				Object[] tuple = (Object[]) objects.next();
				try {
					jsonObj.put("solrParticipantId", (String) tuple[0]);
					jsonObj.put("name", (String) tuple[1]);
					jsonObj.put("uniprot", (String) tuple[2]);
					jsonObj.put("description", (String) tuple[3]);
				

					out.print(jsonObj.toString());
				}

				catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}
		
		
		
		
		if (mode.equalsIgnoreCase("Shrna")) {
			String QueryStr = "select shrna_id as solr_participantid,perturbagenid,pert_iname,seed_seq_6mer,seed_seq_7mer,target_seq from ShrnaPerturbagens where shrna_id ='"+input.trim()+"'";

			SQLQuery query = ses.createSQLQuery(QueryStr)
					.addScalar("solr_participantid", Hibernate.STRING)
					.addScalar("perturbagenid", Hibernate.STRING)
					.addScalar("pert_iname", Hibernate.STRING)
					.addScalar("seed_seq_6mer", Hibernate.STRING)
					.addScalar("seed_seq_7mer", Hibernate.STRING)
					.addScalar("target_seq", Hibernate.STRING);

			Iterator objects = null;
			try {
				objects = query.list().iterator();
			} catch (HibernateException he) {
				log.info("Possibly a communications link failure, check database connectivity");
				out.print("Error retrieving data, please try again");
				sessions.close();
				return;
			}
			String[] arr = new String[6];
			if (objects.hasNext()) {

				Object[] tuple = (Object[]) objects.next();
				try {
					jsonObj.put("solrParticipantId", (String) tuple[0]);
					jsonObj.put("perturbagenid", (String) tuple[1]);
					jsonObj.put("name", (String) tuple[2]);
					jsonObj.put("seed_seq_6mer", (String) tuple[3]);
					jsonObj.put("seed_seq_7mer", (String) tuple[4]);
					jsonObj.put("target_seq", (String) tuple[5]);
				

					out.print(jsonObj.toString());
				}

				catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}
		if (mode.equalsIgnoreCase("Cdna")) {
			String QueryStr = "select distinct PerturbagenId,pert_iname from cdnaPerturbagens where PerturbagenId = '"+input.trim()+"'";

			SQLQuery query = ses.createSQLQuery(QueryStr)
					.addScalar("PerturbagenId", Hibernate.STRING)
					.addScalar("pert_iname", Hibernate.STRING);

			Iterator objects = null;
			try {
				objects = query.list().iterator();
			} catch (HibernateException he) {
				log.info("Possibly a communications link failure, check database connectivity");
				out.print("Error retrieving data, please try again");
				sessions.close();
				return;
			}
			String[] arr = new String[6];
			if (objects.hasNext()) {

				Object[] tuple = (Object[]) objects.next();
				try {
					jsonObj.put("solrParticipantId", (String) tuple[0]);
					jsonObj.put("name", (String) tuple[1]);
				

					out.print(jsonObj.toString());
				}

				catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}
		if (mode.equalsIgnoreCase("Gene")) {
			String QueryStr = "select name,entrezid,description,organism,g.participant_id  from gene g "
    +" inner join participant_to_sourceid ps on (ps.lifeid = g.participant_id) " 
    +"  where ps.mode = 'Gene' " + "and ps.source = '"+source.trim()+"' and  ps.sourceid = '"+input.trim()+"'";

			SQLQuery query = ses.createSQLQuery(QueryStr)
					.addScalar("name", Hibernate.STRING)
					.addScalar("entrezid", Hibernate.STRING)
					.addScalar("description", Hibernate.STRING)
					.addScalar("organism", Hibernate.STRING)
					.addScalar("participant_id", Hibernate.STRING);;

			Iterator objects = null;
			try {
				objects = query.list().iterator();
			} catch (HibernateException he) {
				log.info("Possibly a communications link failure, check database connectivity");
				out.print("Error retrieving data, please try again");
				sessions.close();
				return;
			}
			String[] arr = new String[6];
			if (objects.hasNext()) {

				Object[] tuple = (Object[]) objects.next();
				try {
					jsonObj.put("name", (String) tuple[0]);
					jsonObj.put("entrezid", (String) tuple[1]);
					jsonObj.put("description", (String) tuple[2]);
					jsonObj.put("organism", (String) tuple[3]);
					jsonObj.put("solrParticipantId", (String) tuple[4]);
					out.print(jsonObj.toString());
				}

				catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}
		if (mode.equalsIgnoreCase("assay")) {
			String QueryStr = "select assayname,assayformat,assaytype,assaydescription,projetnumber,organization,downloadurl from assay_details where assayname = '"
					+ URLDecoder.decode(input,"UTF-8") +"'";

			SQLQuery query = ses.createSQLQuery(QueryStr)
					.addScalar("assayname", Hibernate.STRING)
					.addScalar("assayformat", Hibernate.STRING)
					.addScalar("assaytype", Hibernate.STRING)
					.addScalar("assaydescription", Hibernate.STRING)
					.addScalar("projetnumber", Hibernate.STRING)
					.addScalar("organization", Hibernate.STRING)
					.addScalar("downloadurl", Hibernate.STRING);

			Iterator objects = null;
			try {
				objects = query.list().iterator();
			} catch (HibernateException he) {
				log.info("Possibly a communications link failure, check database connectivity");
				out.print("Error retrieving data, please try again");
				sessions.close();
				return;
			}
			String[] arr = new String[8];
			if (objects.hasNext()) {

				Object[] tuple = (Object[]) objects.next();
				try {
					jsonObj.put("assayname", (String) tuple[0]);
					jsonObj.put("assayformat", (String) tuple[1]);
					jsonObj.put("assaytype", (String) tuple[2]);
					jsonObj.put("assaydescription", (String) tuple[3]);
					jsonObj.put("projetnumber", (String) tuple[4]);
					jsonObj.put("organization", (String) tuple[5]);
					jsonObj.put("downloadurl", (String) tuple[6]);
					out.print(jsonObj.toString());
				}

				catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}
		if (mode.equalsIgnoreCase("cellline")) {
			String QueryStr = "select name,disease,geneticmutation, tissue,organ,gender,organism,provider,providerid,lincssourceid,geneticmodification,growthproperties,celltype,doid," +
					" c.participant_id as celllifeid "
				+" from cell c  "
				+"    inner join participant_to_sourceid ps on (ps.lifeid = c.participant_id) "
					+" where ps.mode = 'Cell'"+ "and ps.source = '"+source.trim()+"' and  ps.sourceid = '"+input.trim()+"'";

			SQLQuery query = ses.createSQLQuery(QueryStr)
					.addScalar("name", Hibernate.STRING)
					.addScalar("disease", Hibernate.STRING)
					.addScalar("geneticmutation", Hibernate.STRING)
					.addScalar("tissue", Hibernate.STRING)
					.addScalar("organ", Hibernate.STRING)
					.addScalar("gender", Hibernate.STRING)
					.addScalar("organism", Hibernate.STRING)
					.addScalar("provider", Hibernate.STRING)
					.addScalar("providerid", Hibernate.STRING)
					.addScalar("lincssourceid", Hibernate.STRING)
					.addScalar("geneticmodification", Hibernate.STRING)
					.addScalar("growthproperties", Hibernate.STRING)
					.addScalar("celltype", Hibernate.STRING)
					.addScalar("doid", Hibernate.STRING)
					.addScalar("celllifeid", Hibernate.STRING);

			Iterator objects = null;
			try {
				objects = query.list().iterator();
			} catch (HibernateException he) {
				log.info("Possibly a communications link failure, check database connectivity");
				out.print("Error retrieving data, please try again");
				sessions.close();
				return;
			}
			String[] arr = new String[6];
			if (objects.hasNext()) {

				Object[] tuple = (Object[]) objects.next();
				try {
					jsonObj.put("name", (String) tuple[0]);
					jsonObj.put("disease", (String) tuple[1]);
					jsonObj.put("geneticmutation", (String) tuple[2]);
					jsonObj.put("tissue", (String) tuple[3]);
					jsonObj.put("organ", (String) tuple[4]);
					jsonObj.put("gender", (String) tuple[5]);
					jsonObj.put("organism", (String) tuple[6]);
					jsonObj.put("provider", (String) tuple[7]);
					jsonObj.put("providerid", (String) tuple[8]);
					jsonObj.put("lincssourceid", (String) tuple[9]);
					jsonObj.put("geneticmodification", (String) tuple[10]);
					jsonObj.put("growthproperties", (String) tuple[11]);
					jsonObj.put("celltype", (String) tuple[12]);
					jsonObj.put("doid", (String) tuple[13]);
					jsonObj.put("solrParticipantId", (String) tuple[14]);
					out.print(jsonObj.toString());
				}
				catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}
		sessions.close();

	}

	/**
	 * Called by the init(...) method to generate hibernate Session object
	 * 
	 * @return Hibernate SessionFactory object
	 * 
	 */
	public static SessionFactory connect() {

		Configuration cfg = new Configuration();
		SessionFactory sessions = cfg.configure().buildSessionFactory();

		return sessions;
	}

}
