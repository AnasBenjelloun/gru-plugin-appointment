package fr.paris.lutece.plugins.appointment.business.planningdefinition;

import java.util.List;

import fr.paris.lutece.plugins.appointment.service.AppointmentPlugin;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

/**
 * This class provides instances management methods for Week Definition objects
 * 
 * @author Laurent Payen
 *
 */
public class WeekDefinitionHome {

	// Static variable pointed at the DAO instance
	private static IWeekDefinitionDAO _dao = SpringContextService.getBean(IWeekDefinitionDAO.BEAN_NAME);
	private static Plugin _plugin = PluginService.getPlugin(AppointmentPlugin.PLUGIN_NAME);

	/**
	 * Private constructor - this class does not need to be instantiated
	 */
	private WeekDefinitionHome() {
	}

	/**
	 * Create an instance of the WeekDefinition class
	 * 
	 * @param weekDefinition
	 *            The instance of the WeekDefinition which contains the
	 *            informations to store
	 * @return The instance of the WeekDefinition which has been created with
	 *         its primary key.
	 */
	public static WeekDefinition create(WeekDefinition weekDefinition) {
		_dao.insert(weekDefinition, _plugin);

		return weekDefinition;
	}

	/**
	 * Update of the WeekDefinition which is specified in parameter
	 * 
	 * @param weekDefinition
	 *            The instance of the WeekDefinition which contains the data to
	 *            store
	 * @return The instance of the WeekDefinition which has been updated
	 */
	public static WeekDefinition update(WeekDefinition weekDefinition) {
		_dao.update(weekDefinition, _plugin);

		return weekDefinition;
	}

	/**
	 * Delete the WeekDefinition whose identifier is specified in parameter
	 * 
	 * @param nKey
	 *            The WeekDefinition Id
	 */
	public static void delete(int nKey) {
		_dao.delete(nKey, _plugin);
	}

	/**
	 * Returns an instance of the WeekDefinition whose identifier is specified
	 * in parameter
	 * 
	 * @param nKey
	 *            The WeekDefinition primary key
	 * @return an instance of the WeekDefinition
	 */
	public static WeekDefinition findByPrimaryKey(int nKey) {
		return _dao.select(nKey, _plugin);
	}
	
	/**
	 * Get all the week definitions of the form given
	 * @param nIdForm the Form Id 
	 * @return the list of the week definitions of the form
	 */
	public static List<WeekDefinition> findByIdForm(int nIdForm) {
		return _dao.findByIdForm(nIdForm, _plugin);
	}

}
