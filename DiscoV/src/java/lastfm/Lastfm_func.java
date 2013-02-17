package lastfm;

import de.umass.lastfm.Tag;
import de.umass.lastfm.Track;
import java.util.Collection;

public class Lastfm_func {
  
  public String key = "692515bb0a1d5a21a327cf0901674370"; // lastfm key
  
  public Track Track_search(String artist, String track) {
    
    return Track.getInfo(artist, track, key);
  }
  
  public Collection<Tag> GetGenre(String artist, String song) {
  
    return Track.getTopTags(artist,song,key);
  
  }
  
}
