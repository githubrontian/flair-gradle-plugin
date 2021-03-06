package flair.gradle.tasks.processes

import flair.gradle.tasks.TaskGroup
import flair.gradle.tasks.VariantTask
import flair.gradle.utils.Variant
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessIcons extends VariantTask
{
	@InputFiles
	Set<File> inputFiles

	@OutputDirectory
	File outputDir

	@Override
	void setVariant( Variant variant )
	{
		super.variant = variant

		inputFiles = findInputFiles( )
		outputDir = project.file( "${ outputVariantDir }/package/icons" )

		description = "Processes icons into ${ variant.name } ${ project.buildDir.name } directory"
	}

	ProcessIcons()
	{
		group = TaskGroup.DEFAULT.name
	}

	@SuppressWarnings( "GroovyUnusedDeclaration" )
	@TaskAction
	void processIcons()
	{
		outputDir.deleteDir( )

		for( File file : inputFiles )
		{
			if( file.exists( ) && project.fileTree( file.path ).size( ) > 0 )
			{
				project.copy {
					from file
					into outputDir

					include "*.png"
				}

				break
			}
		}
	}

	private Set<File> findInputFiles()
	{
		List<File> list = new ArrayList<File>( )

		variant.directories.each { list.add( project.file( "${ moduleDir }/src/${ it }/icons" ) ) }

		return list.reverse( )
	}
}
