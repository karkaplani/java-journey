package dal;

import entity.DonationRecord;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonationRecordDAL extends GenericDAL<DonationRecord> {

    public DonationRecordDAL() {
        super( DonationRecord.class );
    }
    
    @Override
    public List<DonationRecord> findAll() {
        //first argument is a name given to a named query defined in appropriate entity
        //second argument is map used for parameter substitution.
        //parameters are names starting with : in named queries, :[name]
        return findResults( "DonationRecord.findAll", null );
    }

    @Override
    public DonationRecord findById( int id ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "id", id );
        //first argument is a name given to a named query defined in appropriate entity
        //second argument is map used for parameter substitution.
        //parameters are names starting with : in named queries, :[name]
        //in this case the parameter is named "id" and value for it is put in map
        return findResult( "DonationRecord.findById", map );
    }

    public DonationRecord findByPerson( int personId ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "person", personId );
        return findResult( "DonationRecord.findByPerson", map );
    }

    public DonationRecord findByDonation( int donationId ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "donation", donationId );
        return findResult( "DonationRecord.findByDonation", map );
    }

    public DonationRecord findByTested( boolean tested ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "tested", tested );
        return findResult( "DonationRecord.findByTested", map );
    }

    public List<DonationRecord> findByAdministrator( String administrator ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "administrator", administrator );
        return findResults( "DonationRecord.findByAdministrator", map );
    }
    
    public List<DonationRecord> findByHospital( String hospital ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "hospital", hospital );
        return findResults( "DonationRecord.findByHospital", map );
    }
    
    public List<DonationRecord> findByCreated( Date created ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "created", created );
        return findResults( "DonationRecord.findByCreated", map );
    }
}