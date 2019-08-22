/**
 * 
 */
package edu.miami.ccs.life;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author svenkatapuram, ndatar UM Center for Computational Science
 *
 */
public class SolrShield
{
	
	private String solrServerUrl;
	private String queryText;
	private String rows;
	private String start;
	private String fields;
	private String[] facets;
	private String[] groups;

	/**
	 * @param solrServerURL - Solr server's url with no "/" following at the end
	 * @param queryText - search term
	 * @param tags - tags to be filtered on
	 * @param category 
	 * @param rows - Result rows
	 */
	public SolrShield(String solrServerURL, String queryText, String fields, String rows, String start, String[] facets, String[] groups)
	{
		super();
		this.solrServerUrl = solrServerURL; 
		this.queryText = queryText;
		this.rows = rows;
		this.fields = fields;
		this.facets = facets;
		this.groups = groups;
		this.start = start;
	}

	public JSONObject getSimpleResults() throws MalformedURLException, IOException, URISyntaxException
	{
		String url = solrServerUrl + "/select?wt=json&indent=false&";
		if(fields != null)
			url += "fl=" + fields.toString() + "&";
		if(rows != null)
			url += "rows=" + rows + "&";
		url += "q=" + queryText;
		
		
		//replace all pipe characters in URL with the ascii equivalent hex code %7C
		url=url.replaceAll("\\|", "%7C");
		//replace all spaces in URL with the ascii equivalent hex code %20
		url = url.replaceAll("\\s", "%20");
		System.out.println(url);
		URLConnection urlConn = new URL(url).openConnection();
		urlConn.setRequestProperty("Accept-Charset", "UTF-8");
		BufferedReader response = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		String line = null;
		String jsonResponse = "";

		while((line = response.readLine()) != null)
		{
			jsonResponse += line;			
		}
		JSONObject temp = null;
		try {
			temp = new JSONObject(jsonResponse);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			jsonResponse +="{\"error\":\"no data found\"}";
		}
		try {
			jsonResponse = "{\"hits\":" + temp.getJSONObject("response").getString("numFound") + ", \"results\": [";
			for(int i = 0; i < temp.getJSONObject("response").getJSONArray("docs").length(); i++)
			{
				JSONObject currResult = temp.getJSONObject("response").getJSONArray("docs").getJSONObject(i);
				jsonResponse += "{";
				
				if(fields != null)
				{
					String[] fieldsArr = fields.split(",");
					for(int j = 0; j < fieldsArr.length; j++)
					{
						if(currResult.get(fieldsArr[j]) instanceof JSONArray)
							jsonResponse += "\"" + fieldsArr[j] + "\": "+currResult.getString(fieldsArr[j]);
						else
							jsonResponse += "\"" + fieldsArr[j] + "\": \""+currResult.getString(fieldsArr[j]) +"\"";
						if(j != fieldsArr.length - 1)
							jsonResponse += ",";
					}
				}
				else
				{
					for(int j = 0; j < currResult.names().length(); j++)
					{
						if(currResult.get(currResult.names().getString(j)) instanceof JSONArray)
							jsonResponse += "\"" + currResult.names().getString(j) + "\": "+currResult.get(currResult.names().getString(j));
						else
							jsonResponse += "\"" + currResult.names().getString(j) + "\": \""+currResult.get(currResult.names().getString(j)) +"\"";
						if(j != currResult.names().length()-1)
							jsonResponse += ",";
					}
				
				}
				jsonResponse += "}";
				if(i != temp.getJSONObject("response").getJSONArray("docs").length()-1)
					jsonResponse += ",";
				}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			jsonResponse +="{\"error\":\"no data found\"}";
		}
		
		jsonResponse += "]}";
		JSONObject obj = null;
		try {
			obj = new JSONObject(jsonResponse);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			jsonResponse +="{\"error\":\"no data found\"}";
		}
		return obj;
	}
	
	public JSONObject getGroupFacetResults() throws MalformedURLException, IOException, URISyntaxException
	{
		String url = solrServerUrl + "/select?wt=json&indent=false&group=true&facet=true&facet.mincount=1&";
		if(start != null)
			url += "start="+start+"&";
		if(fields != null)
			url += "fl=" + fields.toString() + "&";
		if(rows != null)
			url += "rows=" + rows + "&";
		if(facets != null)
			for(int i = 0; i < facets.length; i++)
				url += "facet.field="+ facets[i] + "&";
		if(groups != null)
			for(int i = 0; i < groups.length; i++)
				url += "group.field="+ groups[i] + "&";
		url += "q=" + queryText;
		
		
		//replace all pipe characters in URL with the ascii equivalent hex code %7C
		url=url.replaceAll("\\|", "%7C");
		//replace all spaces in URL with the ascii equivalent hex code %20
		url = url.replaceAll("\\s", "%20");
		System.out.println(url);
		URLConnection urlConn = new URL(url).openConnection();
		urlConn.setRequestProperty("Accept-Charset", "UTF-8");
		BufferedReader response = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		String line = null;
		String jsonResponse = "";
		while((line = response.readLine()) != null)
		{
			jsonResponse += line;
			
		}
		JSONObject temp = null;
		try {
			temp = new JSONObject(jsonResponse);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			jsonResponse +="{\"error\":\"no data found\"}";
		}
		try {
			
			//Descend into progressive loops to find the doclist for each group and it's group value
			jsonResponse = "{\"results\": [";
			for(int i = 0; i < groups.length; i++)
			{
				JSONArray currArray = temp.getJSONObject("grouped").getJSONObject(groups[i]).getJSONArray("groups");
				for(int j = 0; j < currArray.length(); j++)
				{
					JSONObject currObj = currArray.getJSONObject(j);
					if(currObj.getString("groupValue") != "null")
					{
						JSONArray docArray = currObj.getJSONObject("doclist").getJSONArray("docs");
						jsonResponse += "{";
						for(int k = 0; k < docArray.length(); k++)
						{
							JSONObject currResult = docArray.getJSONObject(k);
							//If we have a list of fields, print only those values
							if(fields != null)
							{
								String[] fieldsArr = fields.split(",");
								for(int l = 0; l < fieldsArr.length; l++)
								{
									if(!currResult.isNull(fieldsArr[l]))
									{
										//We have to check if the value at this stage is an array or string and print accordingly
										if(currResult.get(fieldsArr[l]) instanceof JSONArray)
											jsonResponse += "\"" + fieldsArr[l] + "\": "+currResult.getString(fieldsArr[l]);
										else
											jsonResponse += "\"" + fieldsArr[l] + "\": \""+currResult.getString(fieldsArr[l]).replaceAll("\n", "\\n") +"\"";
										if(l != fieldsArr.length - 1)
											jsonResponse += ",";
									}
								}
							}
							//Print all values since fields list is empty
							else
							{
								for(int l = 0; l < currResult.names().length(); l++)
								{
									if(currResult.get(currResult.names().getString(l)) instanceof JSONArray)
										jsonResponse += "\"" + currResult.names().getString(l) + "\": "+currResult.get(currResult.names().getString(l));
									else
										jsonResponse += "\"" + currResult.names().getString(l) + "\": \""+currResult.get(currResult.names().getString(l)).toString().replaceAll("\n", "\\n") +"\"";
									if(l != currResult.names().length()-1)
										jsonResponse += ",";
								}
							}
							jsonResponse += "}";
						}												
					}
					if(j != currArray.length()-1)
						jsonResponse += ",";
				}
			}
			
			JSONObject currObj = temp.getJSONObject("facet_counts").getJSONObject("facet_fields");
			jsonResponse += "], \"valueFacets\": [";
			for(int i = 0; i < facets.length; i++)
			{
				if(currObj.getJSONArray(facets[i]).length() > 0)
				{
					jsonResponse += "\""+facets[i]+"\"";
					if(i != facets.length - 1)
						jsonResponse += ",";
				}
			}
			
			jsonResponse += "], \"valueFacetElements\": {";
			for(int i = 0; i < facets.length; i++)
			{		
				if(currObj.getJSONArray(facets[i]).length() > 0)
				{
					jsonResponse += "\""+facets[i]+"\": [";
					for(int k = 0; k < currObj.getJSONArray(facets[i]).length(); k+=2)
					{
						 jsonResponse += "\"" + currObj.getJSONArray(facets[i]).getString(k) + "\"";
						 if(k != currObj.getJSONArray(facets[i]).length()-2)
							 jsonResponse += ",";
					}
					jsonResponse += "],";
				}
			}
			jsonResponse = jsonResponse.substring(0, jsonResponse.lastIndexOf(','));
			jsonResponse += "}";
					
				
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			jsonResponse +="{\"error\":\"no data found\"}";
		}
		
		jsonResponse += "}";

		JSONObject obj = null;
		try {
			obj = new JSONObject(jsonResponse);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			jsonResponse +="{\"error\":\"no data found\"}";
		}
		
		return obj;
	}

	public JSONObject getTags() throws MalformedURLException, IOException, URISyntaxException
	{
		//A specialized method to get tags for a search term
		JSONObject temp = new JSONObject();
		return temp;
	}
	
	
}
