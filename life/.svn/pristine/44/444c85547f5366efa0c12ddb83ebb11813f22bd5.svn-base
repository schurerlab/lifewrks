/**
Copyright (c) 2011, The University of Miami
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
 * Neither the name of the University of Miami nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE UNIVERSITY OF MIAMI BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package edu.miami.ccs.life;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import chemaxon.jchem.db.JChemSearch;
import chemaxon.license.LicenseManager;
import chemaxon.sss.search.JChemSearchOptions;
import chemaxon.util.ConnectionHandler;

/**
 * Queries ChemAxon to find substances for the substructures drawn 
 * 
 * @author ext-smittal, Sreeharsha Venkatapuram - Center for Computational Science
 * @version 1.0
 */
public class MarvinUIResults extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}
	


	/**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 4066116387420260503L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{

			String strTable = "IJC_LINCS_STRUCTURES";

			DBConnection supdb = new DBConnection();
			ConnectionHandler supconn = supdb.connect();

			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

			String mol = request.getParameter("molecule");

			int stype = 2;

			JChemSearch searcher = new JChemSearch();

			searcher.setRunMode(JChemSearch.RUN_MODE_ASYNCH_COMPLETE);
			searcher.setNumberOfProcessingThreads(10);
			searcher.setQueryStructure(mol);
			searcher.setConnectionHandler(supconn);

			searcher.setStructureTable(strTable);

			JChemSearchOptions searchOptions = new JChemSearchOptions(stype);
			searchOptions.setDissimilarityThreshold((float) 0.5);
			searchOptions.setValenceMatching(true);
			searchOptions.setRadicalMatching(1);
			searchOptions.setTautomerSearch(0);
			searchOptions.setIsotopeMatching(1);
			searchOptions.setChargeMatching(1);
			searchOptions.setVagueBondLevel(5);
			searchOptions.setMaxResultCount(40);

			searcher.setSearchOptions(searchOptions);
			searcher.run();
			while (searcher.isRunning())
			{

			}

			@SuppressWarnings("unused")
			int i = searcher.getResultCount();
			System.out.println("cids:");
			int[] cdIds = searcher.getResults();
			String json = "";
			Statement sidStmt = supconn.getConnection().createStatement();
			ResultSet rs = null;
			for (int j = 0; j < cdIds.length; j++)
			{
				
				String sidQuery = "select distinct LINCSID as lsmid from IJC_LINCS_STRUCTURES where cd_id=" + cdIds[j];
				rs = sidStmt.executeQuery(sidQuery);
				if(rs.next())
				{
					json += "%26quot%3B"+rs.getString("lsmid")+"%26quot%3B";
					if(j!=(cdIds.length-1))
						json += " || ";
				}
				rs.close();
			}
			sidStmt.close();
			RequestDispatcher rd;
			if (!json.equals("")){
			out.print("/life/search?load=SmallMolecule&search=%28"+json+"%29&q=%28"+json+"%29&wt=json&indent=true&group=false&facet=true&facet.field=ProteinId&facet.field=SmallMoleculeId&facet.field=GeneId&facet.field=CellLineId&facet.field=AssayTypeName&group.field=ProteinId&facet.mincount=1&facet.limit=-1&rows=20&start=0&group.ngroups=true");
			}else {
				out.print("/life/search?load=SmallMolecule&search=Search+for+a+compound%2C+disease%2C+protein+...&q=Search+for+a+compound%2C+disease%2C+protein+...&wt=json&indent=true&group=false&facet=true&facet.field=ProteinId&facet.field=SmallMoleculeId&facet.field=GeneId&facet.field=CellLineId&facet.field=AssayTypeName&group.field=ProteinId&facet.mincount=1&facet.limit=-1&rows=20&start=0&group.ngroups=true");
			}
			//rd.forward(request, response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}