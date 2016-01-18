package flair.gradle.structure

import flair.gradle.extensions.PropertyManager
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class IOSStructure implements IStructure
{
	@Override
	public void create( Project project , File source )
	{
		String moduleName = PropertyManager.getProperty( project , "moduleName" )

		if( !moduleName || project.fileTree( "${ moduleName }/src/ios" ).size( ) > 0 ) return

		project.copy {
			from "${ source.path }/src/ios"
			into "${ moduleName }/src/ios"
		}
	}
}