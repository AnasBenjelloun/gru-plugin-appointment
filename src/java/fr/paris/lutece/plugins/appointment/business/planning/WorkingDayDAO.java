package fr.paris.lutece.plugins.appointment.business.planning;

import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.plugins.appointment.business.UtilDAO;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

/**
 * This class provides Data Access methods for Working Day objects
 * 
 * @author Laurent Payen
 *
 */
public final class WorkingDayDAO extends UtilDAO implements IWorkingDayDAO
{

    private static final String SQL_QUERY_NEW_PK = "SELECT max(id_working_day) FROM appointment_working_day";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appointment_working_day (id_working_day, day_of_week, id_week_definition) VALUES (?, ?, ?)";
    private static final String SQL_QUERY_UPDATE = "UPDATE appointment_working_day SET day_of_week = ?, id_week_definition = ? WHERE id_working_day = ?";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appointment_working_day WHERE id_working_day = ? ";
    private static final String SQL_QUERY_SELECT_COLUMNS = "SELECT id_working_day, day_of_week, id_week_definition FROM appointment_working_day";
    private static final String SQL_QUERY_SELECT = SQL_QUERY_SELECT_COLUMNS + " WHERE id_working_day = ?";
    private static final String SQL_QUERY_SELECT_BY_ID_WEEK_DEFINITION = SQL_QUERY_SELECT_COLUMNS + " WHERE id_week_definition = ?";

    @Override
    public synchronized void insert( WorkingDay workingDay, Plugin plugin )
    {
        workingDay.setIdWorkingDay( getNewPrimaryKey( SQL_QUERY_NEW_PK, plugin ) );
        DAOUtil daoUtil = buildDaoUtil( SQL_QUERY_INSERT, workingDay, plugin, true );
        executeUpdate( daoUtil );
    }

    @Override
    public void update( WorkingDay workingDay, Plugin plugin )
    {
        DAOUtil daoUtil = buildDaoUtil( SQL_QUERY_UPDATE, workingDay, plugin, false );
        executeUpdate( daoUtil );
    }

    @Override
    public void delete( int nIdWorkingDay, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nIdWorkingDay );
        executeUpdate( daoUtil );
    }

    @Override
    public WorkingDay select( int nIdWorkingDay, Plugin plugin )
    {
        DAOUtil daoUtil = null;
        WorkingDay workingDay = null;
        try
        {
            daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
            daoUtil.setInt( 1, nIdWorkingDay );
            daoUtil.executeQuery( );
            if ( daoUtil.next( ) )
            {
                workingDay = buildWorkingDay( daoUtil );
            }
        }
        finally
        {
            if ( daoUtil != null )
            {
                daoUtil.free( );
            }
        }
        return workingDay;
    }

    @Override
    public List<WorkingDay> findByIdWeekDefinition( int nIdWeekDefinition, Plugin plugin )
    {
        DAOUtil daoUtil = null;
        List<WorkingDay> listWorkingDays = new ArrayList<>( );
        try
        {
            daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ID_WEEK_DEFINITION, plugin );
            daoUtil.setInt( 1, nIdWeekDefinition );
            daoUtil.executeQuery( );
            while ( daoUtil.next( ) )
            {
                listWorkingDays.add( buildWorkingDay( daoUtil ) );
            }
        }
        finally
        {
            if ( daoUtil != null )
            {
                daoUtil.free( );
            }
        }
        return listWorkingDays;
    }

    /**
     * Build a WorkingDay business object from the resultset
     * 
     * @param daoUtil
     *            the prepare statement util object
     * @return a new WorkingDay with all its attributes assigned
     */
    private WorkingDay buildWorkingDay( DAOUtil daoUtil )
    {
        int nIndex = 1;
        WorkingDay workingDay = new WorkingDay( );
        workingDay.setIdWorkingDay( daoUtil.getInt( nIndex++ ) );
        workingDay.setDayOfWeek( daoUtil.getInt( nIndex++ ) );
        workingDay.setIdWeekDefinition( daoUtil.getInt( nIndex ) );
        return workingDay;
    }

    /**
     * Build a daoUtil object with the working day business object
     * 
     * @param query
     *            the query
     * @param workingDay
     *            the WorkingDay
     * @param plugin
     *            the plugin
     * @param isInsert
     *            true if it is an insert query (in this case, need to set the id). If false, it is an update, in this case, there is a where parameter id to
     *            set
     * @return a new daoUtil with all its values assigned
     */
    private DAOUtil buildDaoUtil( String query, WorkingDay workingDay, Plugin plugin, boolean isInsert )
    {
        int nIndex = 1;
        DAOUtil daoUtil = new DAOUtil( query, plugin );
        if ( isInsert )
        {
            daoUtil.setInt( nIndex++, workingDay.getIdWorkingDay( ) );
        }
        daoUtil.setInt( nIndex++, workingDay.getDayOfWeek( ) );
        daoUtil.setInt( nIndex++, workingDay.getIdWeekDefinition( ) );
        if ( !isInsert )
        {
            daoUtil.setInt( nIndex, workingDay.getIdWorkingDay( ) );
        }
        return daoUtil;
    }

    /**
     * Execute a safe update (Free the connection in case of error when execute the query)
     * 
     * @param daoUtil
     *            the daoUtil
     */
    private void executeUpdate( DAOUtil daoUtil )
    {
        try
        {
            daoUtil.executeUpdate( );
        }
        finally
        {
            if ( daoUtil != null )
            {
                daoUtil.free( );
            }
        }
    }

}