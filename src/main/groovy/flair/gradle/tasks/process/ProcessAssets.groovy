package flair.gradle.tasks.process

import flair.gradle.dependencies.Configurations
import flair.gradle.tasks.AbstractVariantTask
import flair.gradle.tasks.Groups
import flair.gradle.variants.Variant
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectories
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessAssets extends AbstractVariantTask
{
	@InputFiles
	def Set<File> inputDirs

	@OutputDirectories
	def Set<File> outputDirs

	@Override
	public void setVariant( Variant variant )
	{
		super.variant = variant

		inputDirs = getInputFiles( )
		outputDirs = new HashSet<File>( )

		inputDirs.each {

			File file = project.file( "${ outputVariantDir }/package/${ it.name }" )
			outputDirs.add( file )
		}
	}

	public ProcessAssets()
	{
		group = Groups.DEFAULT.name
		description = ""
	}

	@TaskAction
	public void processAssets()
	{
		//outputDirs.each { it.deleteDir( ) }

		getInputFiles( ).each { file ->

			if( file.exists( ) )
			{
				project.copy {
					from file
					into "${ outputVariantDir }/package/${ file.name }"
				}
			}
		}
	}

	private Set<File> getInputFiles()
	{
		Set<File> packagedFiles = project.configurations.getByName( Configurations.PACKAGE.name ).files

		packagedFiles.addAll( project.configurations.getByName( variant.platform.name + Configurations.PACKAGE.name.capitalize( ) ).files )

		variant.productFlavors.each {
			packagedFiles.addAll( project.configurations.getByName( it + Configurations.PACKAGE.name.capitalize( ) ).files )
		}

		if( variant.buildType ) packagedFiles.addAll( project.configurations.getByName( variant.buildType + Configurations.PACKAGE.name.capitalize( ) ) )

		return packagedFiles
	}
}
