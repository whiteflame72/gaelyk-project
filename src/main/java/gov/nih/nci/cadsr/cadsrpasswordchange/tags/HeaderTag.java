package gov.nih.nci.cadsr.cadsrpasswordchange.tags;

import gov.nih.nci.cadsr.cadsrpasswordchange.core.MainServlet;
import gov.nih.nci.cadsr.cadsrpasswordchange.core.PropertyHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;



public class HeaderTag extends TagSupport {
    
	private static final long serialVersionUID = 1L;

    private Logger logger = Logger.getLogger(HeaderTag.class);	
	

	protected String showLogout = null;  // (remains null if attribute not specified)
	
	public String getShowlogout() {
	   return (this.showLogout);
	}
	
	public void setShowlogout(String showLogout) {
	   this.showLogout = showLogout;
	}	
      
    
	public HeaderTag() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int doEndTag() throws JspException {

       try {
    	   JspWriter out = pageContext.getOut();
    	   
    	   String logoutLink = "";
    	   if (showLogout != null && (showLogout.equalsIgnoreCase("yes") || showLogout.equalsIgnoreCase("true") ) )
    			   logoutLink = "<td align=\"right\"><a href=\"javascript:callLogout();\" alt=\"Logout\">Logout</a></td>";

    	   String helpLink = "<td align=\"left\">"
    			   + "<div aria-hidden=true>"
    	   		   + "<a target=\"_blank\" href=\""+ PropertyHelper.getHELP_LINK() +"\">"
    			   + "<img style=\"border: 0px solid black\" title=\"Help Link\" src=\"/cadsrpasswordchange/images/icon_help.gif\" alt=\"Application Help\"></a>"
    			   + "</div>"
    			   + "<br>"
    			   + "<font color=\"brown\" face=\"verdana\" size=\"1\">&nbsp;Help&nbsp;</font></td>";
    	   
    	   out.print("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#A90101\">"
    			   + "<tr bgcolor=\"#A90101\">"
    			   
    			   + "<td valign=\"center\" align=\"left\">"
    			   + "<div aria-hidden=true>"
    			   + "<a href=\"http://www.cancer.gov\" target=\"_blank\" alt=\"NCI Logo\">"
    			   + "<img src=\"/cadsrpasswordchange/images/brandtype.gif\" alt=\"NCI Logo\" border=\"0\"></a>"
    			   + "</div>"
    			   + "</td>"
    			   
    			   + "<td valign=\"center\" align=\"right\">"
    			   + "<div aria-hidden=true>"
    			   + "<a href=\"http://www.cancer.gov\" target=\"_blank\" alt=\"NCI Logo\">"
    			   + "<img src=\"/cadsrpasswordchange/images/tagline_nologo.gif\" alt=\"NCI Logo\" border=\"0\"></a>"
    			   + "</div>"
    			   + "</td></tr>"
    			   + "\n</table>\n"
    			   + "<table class=\"secttable\"><colgroup><col /></colgroup><tbody class=\"secttbody\" />"
    			   + "<tr>"
    			   + "<td align=\"left\">"
    			   + "<div aria-hidden=true>"
    			   + "<a target=\"_blank\" href=\""+ PropertyHelper.getLOGO_LINK() +"\">"
    			   + "<img style=\"border: 0px solid black\" title=\"NCICB caDSR\" src=\"/cadsrpasswordchange/images/cadsrpasswordchange_banner.gif\" alt=\"Application Logo\"></a>"
    			   + "</div>"
    			   + "</td>"
    			   + logoutLink + helpLink + "</tr>"
    			   + "\n</table>\n");
    	   
       } catch (Exception ex) {
    	   logger.error("footer tag creation failed: " + ex.getMessage());       	
       }
       
       logger.debug("HeaderTag showLogout: " + showLogout);       
	   return EVAL_PAGE;
	}


}
